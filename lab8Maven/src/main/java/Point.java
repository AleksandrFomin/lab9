/**
 * Created by sasha on 22.07.17.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "points")
public class Point implements Serializable{

    @Id
    @Column(name="id")
    private long id;

    @Column(name="x")
    private float x;

    @Column(name="y")
    private float y;

    @Column(name="r")
    private float r;

    @Column(name="isin")
    private boolean isInside;

    public Point() {}

    public Point(float x, float y, float r){
        this.x=x;
        this.y=y;
        this.r=r;
        isInside=true;
    }

    public boolean isInside() {
        return isInside;
    }

    public void setInside(float r) {
        if(check(r))
            isInside=true;
        else
            isInside=false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean check(float r){
        if(x>=-r&&x<=0){
            if(y<r&&y>0){
                return true;
            }
            if((x*x+y*y)<=r*r) {
                return true;
            }
        }
        if(x>=0 &&x<=r/2){
            if(y>=0&&y<=(-2*x+2)){
                return true;
            }
        }
        return false;
    }
}