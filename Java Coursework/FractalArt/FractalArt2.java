
 /* Created by Emily on 2/24/15.*/

import java.awt.Color;

public class FractalArt2 {
    public static void main(String[] args) {
        StdDraw.setXscale(-1,1);
        StdDraw.setYscale(-1,1);
        StdDraw.clear(StdDraw.BLACK);

        int N = 6;
        double r = 0.3;
        double[] shape1x = {0,0.15,-0.15,-0.3};
        double[] shape1y = {0,0.259808,0.259808,0};
        double[] shape2x = {0,0.15,0.3,0.15};
        double[] shape2y = {0,0.259808,0,-0.259808};
        double[] shape3x = {0,0.15,-0.15,-0.3};
        double[] shape3y = {0,-0.259808,-0.259808,0};
        hexagon(N,r,shape1x,shape1y,shape2x,shape2y,shape3x,shape3y);

    }
    public static void hexagon(int level,double r,double[]x1,double[]y1,double[]x2,double[]y2,double[]x3,double[]y3){
        if (level==0) return;
        else {
            double[] new2x1={(x1[0]/2)-r,(x1[1]/2)-r,(x1[2]/2)-r,(x1[3]/2)-r};
            double[] new2y1={(y1[0]/2),(y1[1]/2),(y1[2]/2),(y1[3]/2)};
            double[] new2x2={(x2[0]/2)-r,(x2[1]/2)-r,(x2[2]/2)-r,(x2[3]/2)-r};
            double[] new2y2={(y2[0]/2),(y2[1]/2),(y2[2]/2),(y2[3]/2)};
            double[] new2x3={(x3[0]/2)-r,(x3[1]/2)-r,(x3[2]/2)-r,(x3[3]/2)-r};
            double[] new2y3={(y3[0]/2),(y3[1]/2),(y3[2]/2),(y3[3]/2)};
            hexagon((level-1),r,new2x1,new2y1,new2x2,new2y2,new2x3,new2y3);

            double[] newer2x1={(x1[0]/2)+r/2,(x1[1]/2)+r/2,(x1[2]/2)+r/2,(x1[3]/2)+r/2};
            double[] newer2y1={(y1[0]/2)+r-(r/6.5),(y1[1]/2)+r-(r/6.5),(y1[2]/2)+r-(r/6.5),(y1[3]/2)+r-(r/6.5)};
            double[] newer2x2={(x2[0]/2)+r/2,(x2[1]/2)+r/2,(x2[2]/2)+r/2,(x2[3]/2)+r/2};
            double[] newer2y2={(y2[0]/2)+r-(r/6.5),(y2[1]/2)+r-(r/6.5),(y2[2]/2)+r-(r/6.5),(y2[3]/2)+r-(r/6.5)};
            double[] newer2x3={(x3[0]/2)+r/2,(x3[1]/2)+r/2,(x3[2]/2)+r/2,(x3[3]/2)+r/2};
            double[] newer2y3={(y3[0]/2)+r-(r/6.5),(y3[1]/2)+r-(r/6.5),(y3[2]/2)+r-(r/6.5),(y3[3]/2)+r-(r/6.5)};
            hexagon((level-1),r,newer2x1,newer2y1,newer2x2,newer2y2,newer2x3,newer2y3);

            double[] newest2x1={(x1[0]/2)+r/2,(x1[1]/2)+r/2,(x1[2]/2)+r/2,(x1[3]/2)+r/2};
            double[] newest2y1={(y1[0]/2)-r+(r/6.5),(y1[1]/2)-r+(r/6.5),(y1[2]/2)-r+(r/6.5),(y1[3]/2)-r+(r/6.5)};
            double[] newest2x2={(x2[0]/2)+r/2,(x2[1]/2)+r/2,(x2[2]/2)+r/2,(x2[3]/2)+r/2};
            double[] newest2y2={(y2[0]/2)-r+(r/6.5),(y2[1]/2)-r+(r/6.5),(y2[2]/2)-r+(r/6.5),(y2[3]/2)-r+(r/6.5)};
            double[] newest2x3={(x3[0]/2)+r/2,(x3[1]/2)+r/2,(x3[2]/2)+r/2,(x3[3]/2)+r/2};
            double[] newest2y3={(y3[0]/2)-r+(r/6.5),(y3[1]/2)-r+(r/6.5),(y3[2]/2)-r+(r/6.5),(y3[3]/2)-r+(r/6.5)};
            hexagon((level-1),r,newest2x1,newest2y1,newest2x2,newest2y2,newest2x3,newest2y3);

            StdDraw.setPenColor(255, 255, 255);
            StdDraw.filledPolygon(x1, y1);
            StdDraw.setPenColor(160, 160, 160);
            StdDraw.filledPolygon(x2, y2);
            StdDraw.setPenColor(70, 70, 70);
            StdDraw.filledPolygon(x3, y3);

            double[] newx1={(x1[0]/2)+r,(x1[1]/2)+r,(x1[2]/2)+r,(x1[3]/2)+r};
            double[] newy1={(y1[0]/2),(y1[1]/2),(y1[2]/2),(y1[3]/2)};
            double[] newx2={(x2[0]/2)+r,(x2[1]/2)+r,(x2[2]/2)+r,(x2[3]/2)+r};
            double[] newy2={(y2[0]/2),(y2[1]/2),(y2[2]/2),(y2[3]/2)};
            double[] newx3={(x3[0]/2)+r,(x3[1]/2)+r,(x3[2]/2)+r,(x3[3]/2)+r};
            double[] newy3={(y3[0]/2),(y3[1]/2),(y3[2]/2),(y3[3]/2)};
            hexagon((level-1),r,newx1,newy1,newx2,newy2,newx3,newy3);

            double[] newerx1={(x1[0]/2)-r/2,(x1[1]/2)-r/2,(x1[2]/2)-r/2,(x1[3]/2)-r/2};
            double[] newery1={(y1[0]/2)+r-(r/6.5),(y1[1]/2)+r-(r/6.5),(y1[2]/2)+r-(r/6.5),(y1[3]/2)+r-(r/6.5)};
            double[] newerx2={(x2[0]/2)-r/2,(x2[1]/2)-r/2,(x2[2]/2)-r/2,(x2[3]/2)-r/2};
            double[] newery2={(y2[0]/2)+r-(r/6.5),(y2[1]/2)+r-(r/6.5),(y2[2]/2)+r-(r/6.5),(y2[3]/2)+r-(r/6.5)};
            double[] newerx3={(x3[0]/2)-r/2,(x3[1]/2)-r/2,(x3[2]/2)-r/2,(x3[3]/2)-r/2};
            double[] newery3={(y3[0]/2)+r-(r/6.5),(y3[1]/2)+r-(r/6.5),(y3[2]/2)+r-(r/6.5),(y3[3]/2)+r-(r/6.5)};
            hexagon((level-1),r,newerx1,newery1,newerx2,newery2,newerx3,newery3);

            double[] newestx1={(x1[0]/2)-r/2,(x1[1]/2)-r/2,(x1[2]/2)-r/2,(x1[3]/2)-r/2};
            double[] newesty1={(y1[0]/2)-r+(r/6.5),(y1[1]/2)-r+(r/6.5),(y1[2]/2)-r+(r/6.5),(y1[3]/2)-r+(r/6.5)};
            double[] newestx2={(x2[0]/2)-r/2,(x2[1]/2)-r/2,(x2[2]/2)-r/2,(x2[3]/2)-r/2};
            double[] newesty2={(y2[0]/2)-r+(r/6.5),(y2[1]/2)-r+(r/6.5),(y2[2]/2)-r+(r/6.5),(y2[3]/2)-r+(r/6.5)};
            double[] newestx3={(x3[0]/2)-r/2,(x3[1]/2)-r/2,(x3[2]/2)-r/2,(x3[3]/2)-r/2};
            double[] newesty3={(y3[0]/2)-r+(r/6.5),(y3[1]/2)-r+(r/6.5),(y3[2]/2)-r+(r/6.5),(y3[3]/2)-r+(r/6.5)};
            hexagon((level-1),r,newestx1,newesty1,newestx2,newesty2,newestx3,newesty3);

        }
    }
}
