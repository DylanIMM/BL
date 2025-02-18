package DataAcces.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DataAcces.IDAO;
import DataAcces.SQLiteDataHelper;
import DataAcces.DTO.RelacionDTO;


public class RelacionDao extends SQLiteDataHelper implements IDAO <RelacionDTO> {
@Override
    public RelacionDTO readBy(Integer id) throws Exception {
        RelacionDTO oS = new RelacionDTO();
        String query = "SELECT IdRelacion " + 
                        ",IdRelacionTipo" + 
                        ",Estado        " + 
                        ",IdPersona1" + 
                        ",IdPersona2" +
                        ",FechaCrea " + 
                        ",FechaModifica "+
                        "FROM Relacion " + 
                        "WHERE IdRelacio= "
                      + id.toString() ;
        try {
            Connection conn = openConnection();         // conectar a DB     
            Statement  stmt = conn.createStatement();   // CRUD : select * ...    
            ResultSet rs   = stmt.executeQuery(query);  // ejecutar la
            while (rs.next()) {
            
                oS = new RelacionDTO(rs.getInt(1),rs.getInt(2),rs.getString(3),
                rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7));
            }
        } 
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return oS;
    }

    public List<RelacionDTO> readAll() throws Exception {
        List <RelacionDTO> lst = new ArrayList<>();
         String query =" SELECT IdPersonaRol  "
                        +",IdPersonaRolPadre " 
                        +",Estado        "
                        +",Nombre        "
                        +",FechaCrea           "
                        +"FechaModifica"            
                        +" FROM    PersonaRol   "
                        +" WHERE   Estado = 'A'  ";
        try {
            Connection conn = openConnection();         // conectar a DB     
            Statement  stmt = conn.createStatement();   // CRUD : select * ...    
            ResultSet rs   = stmt.executeQuery(query);    // ejecutar la
            while (rs.next()) {
                    RelacionDTO s = new RelacionDTO(rs.getInt(1),rs.getInt(2),rs.getString(3),
                    rs.getInt(4),rs.getInt(5),rs.getString(6),rs.getString(7));
                lst.add(s);
            }
        } 
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return lst; 
    }

    @Override
    public boolean create(RelacionDTO entity) throws Exception {
        String query = " INSERT INTO Relacion (Estado) VALUES (?)";
        try {
            Connection        conn  = openConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, entity.getEstado());
            pstmt.executeUpdate();
            return true;
        } 
        catch (SQLException e) {
            throw new Exception();
        }
    }

    @Override
    public boolean update(RelacionDTO entity) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        String query = " UPDATE RelacionDTO SET Estado = ?, FechaModifica = ? WHERE IdRelacion = ?";
        try {
            Connection          conn = openConnection();
            PreparedStatement pstmt  = conn.prepareStatement(query);
            pstmt.setString(1, entity.getEstado());
            pstmt.setString(2, dtf.format(now).toString());
            pstmt.setInt(3, entity.getIdRelacion());
            pstmt.executeUpdate();
            return true;
        } 
        catch (SQLException e) {
            throw new Exception();
        }
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = " UPDATE Relacion SET Estado  = ? WHERE IdRelacion = ?";
        try {
            Connection          conn = openConnection();
            PreparedStatement  pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "X");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return true;
        } 
        catch (SQLException e) {
            throw new Exception();
        }
    }
}
