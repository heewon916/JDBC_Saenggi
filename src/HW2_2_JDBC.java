import java.sql.*;
import java.util.Scanner;
/*
2. 본인이 원하는 제품 명을 입력하면 제품 관련 정보를 찾아 orders와 orderdetails에
입력하는 프로그램을 작성하고 그 결과를 보이세요. -> join 해서 데이터 가져오면 되는 건가???
 */
public class HW2_2_JDBC {
    /**
     *
     * @param productName <- input the product want to search about
     */
    public static void getProductDetails(String productName){
        ResultSet rs;
        String sqlSearch =
                "select p.productName, p.productCode, o.orderNumber, o.customerNumber, od.quantityOrdered, od.orderLineNumber, od.priceEach " +
                "from orders o, orderdetails od , products p " +
                "where o.orderNumber = od.orderNumber and p.productCode = od.productCode and p.productName = ?;";
        try (Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlSearch)){

            pstmt.setString(1, productName);
            rs = pstmt.executeQuery();
            // System.out.println(rs.next());
            System.out.println("product Name" + "\t" + "productCode" + "\t" +
                    "orderNumber" + "\t" + "customerNumber" + "\t" + "quantityOrdered" + "\t" +
                    "orderLineNumber" + "\t" + "priceEach");
            while(rs.next()){
                System.out.println(rs.getString(("productName")) + "\t" + rs.getString("p.productCode") + "\t"
                        + rs.getInt("o.orderNumber") + "\t" + rs.getInt("o.customerNumber") + "\t"
                        + rs.getInt("od.quantityOrdered") + "\t" + rs.getInt("od.orderLineNumber") +"\t"
                        + rs.getDouble("od.priceEach"));
            }

        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String product_name;
        System.out.print("What product do you want to search? >>");
        product_name = sc.nextLine();
        //System.out.println(product_name);

        getProductDetails(product_name);
    }
}
