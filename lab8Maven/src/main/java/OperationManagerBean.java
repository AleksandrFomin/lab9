import com.sun.org.apache.xerces.internal.util.Status;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sasha on 24.07.17.
 */
//@Stateless(name = "OperationManagerEJB")
@javax.enterprise.context.RequestScoped
@Path("point")
public class OperationManagerBean {
    public OperationManagerBean() {
    }
    private ArrayList<Point> points=new ArrayList<Point>();

    @EJB
    private DBOperations dbOperations;
    //private DBOperations dbOperations=new DBOperations();

    public DBOperations getDbOperations(){
        return dbOperations;
    }

    public void setDbOperations(DBOperations dbOperations) {
        this.dbOperations = dbOperations;
    }

    @GET
    @Path("add/{x}/{y}/{r}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response submitHandler(
            @PathParam("x") String X,
            @PathParam("y") String Y,
            @PathParam("r") String R){
        float xVal= Float.parseFloat(X);
        float yVal= Float.parseFloat(Y);
        float rVal= Float.parseFloat(R);
        addPoint(xVal,yVal,rVal);
        ArrayList<Point> answer=dbOperations.readAllTable();
        return Response.ok(answer)
                .header("Access-Control-Allow-Origin", "*").build();
        //ArrayList<Point> points=new ArrayList<Point>();
        //points.add(new Point(xVal,yVal,rVal));
        //return points;
    }

    public void addPoint(float x, float y, float r){
        Point newPoint=new Point(x,y,r);
        newPoint.setInside(r);
        dbOperations.addPoint(newPoint);
    }

    private void checkAllPoints(ArrayList<Point> points, float r){
        //for(Point p:points)
        // checkPoint(p,r);
    }

    String img;
    @GET
    @Path("img")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getImg()throws IOException {
        //File file = new File();
        //String path=file.getCanonicalPath();
        String path = Thread.currentThread().getContextClassLoader().getResource("area.png").getPath();
        File file=new File(path);
        BufferedImage source = null;
        try {
            source = ImageIO.read(file);
        } catch (Exception e) {
            return Response.ok(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
        }
        drawAllPoints(source);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(source, "png", baos);
        baos.flush();
        byte[] imgToSend = baos.toByteArray();
        baos.close();
        img = DatatypeConverter.printBase64Binary(imgToSend);
        return Response.ok(img).header("Access-Control-Allow-Origin", "*").build();
    }

    private void drawAllPoints(BufferedImage img){
        points.clear();
        points=dbOperations.readAllTable();
        for(Point p : points)
            drawPoint(img, p, points.get(points.size()-1).getR());

    }
    private void drawPoint(BufferedImage img, Point point, float r){
        Graphics2D g = img.createGraphics();
        if(point.check(r))
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.RED);
        int width = img.getWidth();
        int height = img.getHeight();
        g.fillOval(width/2 + (int)(point.getX()/r*80)-10, height/2 - (int)(point.getY()/r * 80) -10 , 10, 10);
    }


    @GET
    @Path("reset")
    @Produces({MediaType.APPLICATION_JSON})
    public Response reset(){
        dbOperations.deleteAllPoints();
        return Response.ok(dbOperations.readAllTable())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("getPoints")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPoints(){
        ArrayList<Point> answer=dbOperations.readAllTable();
        return Response.ok(answer)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("addUser/{login}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addUser(
            @PathParam("login") String login,
            @PathParam("password") String password) {
        if(dbOperations.userExists(login))
            return Response.ok(false)
                    .header("Access-Control-Allow-Origin", "*").build();
        LabUser newUser=new LabUser(login,password);
        dbOperations.addUser(newUser);
        return Response.ok(true)
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("checkUser/{login}/{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkUser(
            @PathParam("login") String login,
            @PathParam("password") String password) {
        LabUser newUser=new LabUser(login,password);
        if(dbOperations.userExists(login)){
            if(dbOperations.checkPassword(newUser))
                return Response.ok(true)
                        .header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.ok(false)
                .header("Access-Control-Allow-Origin", "*").build();

    }
}
