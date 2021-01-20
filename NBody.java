public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];

        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");


        // Step 5. Simulate the universe - loop:
        for (double k = 0.0; k < tau; k += dt) {

            // Step 5A. Calculate net forces. AX = FX/M
            double[] fx = new double[n];
            double[] fy = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j != i) {

                        double dpx = (px[j] - px[i]);
                        double dpy = (py[j] - py[i]);
                        double r = Math.sqrt(dpx * dpx + dpy * dpy);

                        double CONSTANT_G = 6.67e-11;
                        double GMM = (CONSTANT_G * mass[i] * mass[j]);
                        double force = GMM / (r * r);

                        double forceX = (force * dpx) / r;
                        double forceY = (force * dpy) / r;
                        fx[i] += forceX;
                        fy[i] += forceY;
                    }
                }
            }
            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                vx[i] = vx[i] + (fx[i] / mass[i]) * dt;
                vy[i] = vy[i] + (fy[i] / mass[i]) * dt;
                px[i] = px[i] + vx[i] * dt;
                py[i] = py[i] + vy[i] * dt;

            }

            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int i = 0; i < n; i++) {
                StdDraw.picture(px[i], py[i], image[i]);
            }
            StdDraw.pause(20);
            StdDraw.show();
        }
        // Step 6. Print universe to standard output. Received from NBody Docs.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
