/*******************************
 * NBody Simulation - visually represent solar system using Newtonian Physics
 * @author Emily Conklin
 * 2/23/15
 *
 * The purpose of this program is to create an animation of a solar system
 * With a number of planets "N" that all act upon each other's motion via gravity
 * For the particular given data, this program simulates four planets orbiting a sun
 */

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Arrays;

public class NBody {

	public static void main(String[] args) throws Exception {
		// read in planet information
		Scanner s = new Scanner(new FileInputStream("data/planets.txt"));
		// number of planets
		int N = s.nextInt();
		// radius of universe
		double R = s.nextDouble();
		StdDraw.setXscale(-R, R);
		StdDraw.setYscale(-R, R);

		// Gravitational Constant
		double G = 6.67e-11;
		// time step
		double dt = 25000; //time delta

		// data structures to store information related to the planets
		double[] xCoord = new double[N];
		double[] yCoord = new double[N];
		double[] vx = new double[N];
		double[] vy = new double[N];
		double[] mass = new double[N];
		String[] imgFile = new String[N];

		StdDraw.picture(0, 0, "img/starfield.jpg"); //draw initial background


		/* YOUR CODE HERE */
		/* Step 1: Read in planet data from file and store in data structures */
		String[] lines = new String[6 * N + 1]; //initialize new array
		int count = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0, count7 = 0;

		while (s.hasNext()) { //gets each line of file and adds elements to array 'lines'
			String aLine = s.nextLine();
			aLine = aLine.replaceAll("\\s+", " ").trim(); //removes extra whitespace
			String temp[] = aLine.split(" ");
			for (int i = 0; i < temp.length; i++) { //adds each element of each line to array
				lines[count] = temp[i];
				count++;
			}
		}
		String[] parts = Arrays.copyOfRange(lines, 1, lines.length); //gets rid of blank array element

		for (int i = 0; i < parts.length; i += 6) { //adds initial xCoord variables to array
			String x = parts[i];
			double newx = Double.parseDouble(x);
			xCoord[count2] = newx;
			count2++;
		}
		for (int i = 1; i < parts.length; i += 6) { //adds initial yCoord variables to array
			String y = parts[i];
			double newy = Double.parseDouble(y);
			yCoord[count3] = newy;
			count3++;
		}
		for (int i = 2; i < parts.length; i += 6) { //adds initial xvelocity variables to array
			String vxs = parts[i];
			double newvxs = Double.parseDouble(vxs);
			vx[count4] = newvxs;
			count4++;
		}
		for (int i = 3; i < parts.length; i += 6) { //adds initial yvelocity variables to array
			String vys = parts[i];
			double newvys = Double.parseDouble(vys);
			vy[count5] = newvys;
			count5++;
		}
		for (int i = 4; i < parts.length; i += 6) { //adds mass variables to array
			String Smass = parts[i];
			double newmass = Double.parseDouble(Smass);
			mass[count6] = newmass;
			count6++;
		}
		for (int i = 5; i < parts.length; i += 6) { //adds img variables to array
			String img = parts[i];
			imgFile[count7] = img;
			count7++;
		}

		/* Step 2: Simulate orbiting galaxy */
		//StdAudio.play("audio/2001.mid"); //this worked only the first time I ran it
		while (true) { //runs until user closes the window
			StdDraw.picture(0, 0, "img/starfield.jpg"); //draw background at each iteration
			for (int j = 0; j < N; j++) { //goes through each planet
				double netFx = 0;
				double netFy = 0;
				for (int k = 0; k < N; k++) { //goes through each planet for each planet
					if (j != k) {	//makes sure planet is not compared to itself
						//calculate net forces for each planet
						double dx = xCoord[j] - xCoord[k];
						double dy = yCoord[j] - yCoord[k];
						double dxSqrd = Math.pow(dx, 2);
						double dySqrd = Math.pow(dy, 2);
						double r = Math.sqrt(dxSqrd + dySqrd);
						double F = (G * mass[j] * mass[k]) / Math.pow(r, 2);
						double Fx = F * (dx / r);
						double Fy = F * (dy / r);
						//adds the force of each planet to the net force
						netFx += (-Fx);
						netFy += (-Fy);
						//netFx and netFy are negated to compensate for sign effects of squaring/sqrt
					}
				}
				//calculate acceleration for each planet
				double ax = netFx / mass[j];
				double ay = netFy / mass[j];

				//calculate new velocity for each planet
				vx[j] = vx[j] + (dt * ax);
				vy[j] = vy[j] + (dt * ay);

				//calculate new position for each planet
				xCoord[j] = xCoord[j] + (dt * vx[j]);
				yCoord[j] = yCoord[j] + (dt * vy[j]);
			}
			for (int i=0;i<N;i++){
				//draw each planet given new coordinates
				StdDraw.picture(xCoord[i], yCoord[i], "img/" + imgFile[i]);
			}
			//show for 20 ms before repeating
			StdDraw.show(20);
		}
	}
}