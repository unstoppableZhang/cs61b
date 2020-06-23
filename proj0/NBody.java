public class NBody {
    public static double readRadius(String fileName){
        In in = new In(fileName);
        int N = in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String fileName){
        In in = new In(fileName);
        int N = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[N];
        for (int i = 0; i < N; i ++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            bodies[i] = new Body(xxPos,yyPos,xxVel,yyVel,mass,img);
        }
        return bodies;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-radius,radius);
        StdDraw.setYscale(-radius,radius);
        double t = 0;
        while(t<=T){
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for (int i = 0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for (int i = 0; i < bodies.length; i++){
                bodies[i].update(dt,xForces[i],yForces[i]);
            }

            StdDraw.picture(0,0,"images/starfield.jpg");

            for (Body body:bodies){
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
