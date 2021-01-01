import java.util.*;

public class Cube {
	//fields
	private ArrayList<Integer> cube = new ArrayList<Integer>();
	private ArrayList<Integer> shuffle = new ArrayList<Integer>();
	private ArrayList<Integer> solution = new ArrayList<Integer>();
	
	/*constructor:
		the cube will be represented by an array of 48 (0-47) values, representing 8 values on 6 faces,
	  	with each value holding a number from 1 through 6, representing the 6 colors
	  	(Yellow: 1, Orange: 2, Blue: 3, Red: 4, Green: 5, White: 6)
	 */
	public Cube() {
		int colorCount = 0;
		//cube is created solved
		for(int i = 0; i < 48; i++) {
			if(i % 8 == 0) {
				colorCount++;
			}
			cube.add(colorCount);
		}
	}
	
	/*
	 method to print cube contents (printCube)
	 */
	public void printCube() {
		System.out.print("       "+cube.get(0)+" "+cube.get(1)+" "+cube.get(2));
		System.out.print("\n       "+cube.get(3)+"   "+cube.get(4));
		System.out.print("\n       "+cube.get(5)+" "+cube.get(6)+" "+cube.get(7)+"\n");
		int count = 0;
		for(int i = 8; i < 35; i++) {
			System.out.print(cube.get(i)+" ");
			count++;
			if(count % 3 == 0) {
				System.out.print(" ");
				i += 5;
			}
		}
		System.out.print("\n"+cube.get(11)+"   "+cube.get(12)+"  ");
		System.out.print(cube.get(19)+"   "+cube.get(20)+"  ");
		System.out.print(cube.get(27)+"   "+cube.get(28)+"  ");
		System.out.print(cube.get(35)+"   "+cube.get(36)+"\n");
		for(int i = 13; i < 40; i++) {
			System.out.print(cube.get(i)+" ");
			count++;
			if(count % 3 == 0) {
				System.out.print(" ");
				i += 5;
			}
		}
		System.out.print("\n       "+cube.get(40)+" "+cube.get(41)+" "+cube.get(42));
		System.out.print("\n       "+cube.get(43)+"   "+cube.get(44));
		System.out.print("\n       "+cube.get(45)+" "+cube.get(46)+" "+cube.get(47)+"\n");
	}
	public void shuffle() {
		solution.clear();
		shuffle.clear();
		for(int i = 0; i < 20; i++) {
			int turn = (int)(18*Math.random());
			shuffle.add(turn);
			turnSide(turn);
			solution.remove(0);
		}
	}
	
	/*turn sides:
	 Moves are representing numerically as follows
	 	*U: 0, U2: 1, U': 2,   L: 3, L2: 4, L': 5,   F: 6, F2: 7, F': 8,
	 	*R: 9, R2: 10, R': 11,   B: 12, B2: 13, B': 14,   D: 15, D2: 16, D': 17
	 */
	private void turnSide(int i) {
		if(i != -1) {
			solution.add(i);
		}
		for(int j = 0; j < i%3+1; j++) {
			switch(i - i%3) {
				case -1:
					break;
				case 0:
					turnHelper(0, 1, new int[] {16, 24, 32, 8, 17, 25, 33, 9, 18, 26, 34, 10});
					break;
				case 3:
					turnHelper(8, 9, new int[] {40, 16, 0, 39, 45, 21, 5, 34, 43, 19, 3, 36});
					break;
				case 6:
					turnHelper(16, 17, new int[] {5, 15, 42, 24, 7, 10, 40, 29, 6, 12, 41, 27});
					break;
				case 9:
					turnHelper(24, 25, new int[] {18, 42, 37, 2, 23, 47, 32, 7, 20, 44, 35, 4});
					break;
				case 12:
					turnHelper(32, 33, new int[] {0, 26, 47, 13, 2, 31, 45, 8, 1, 28, 46, 11});
					break;
				case 15:
					turnHelper(40, 41, new int[] {21, 13, 37, 29, 23, 15, 39, 31, 22, 14, 38, 30});
					break;
			}
		}
	}
	//helper method to update values
	private void turnHelper(int x, int y, int[] side) {
		int buffer1 = cube.get(x);
		int buffer2 = cube.get(y);
		int[] one = {5, 2, -5, -2};
		int[] two = {2, 3, -2, -3};
		for(int i = 0; i < 3; i++) {
			cube.set(x, cube.get(x+one[i]));
			x = x+one[i];
		}
		cube.set(x, buffer1);
		for(int i = 0; i < 3; i++) {
			cube.set(y, cube.get(y+two[i]));
			y = y+two[i];
		}
		cube.set(y, buffer2);
		
		for(int i = 0; i <= 8; i+=4) {
			int bufferA = cube.get(side[i]);
			for(int j = 0; j < 3; j++) {
				cube.set(side[i+j], cube.get(side[i+j+1]));
			}
			cube.set(side[i+3], bufferA);
		}
	}

	public void solve() {
		cross();
		fl();
		sl();
		oll();
		pll();
		configureSolution();
	}
	private void cross() {
		int[] k = {1, 3, 4, 6, 9, 11, 12, 14, 17, 19, 20 , 22, 25, 27, 28, 30,
				33, 35, 36, 38, 41, 43, 44, 46};
		while(cube.get(41) != 6 || cube.get(43) != 6 ||
				cube.get(44) != 6 || cube.get(46) != 6) {
			int i = 0;
			int s = 1;
			//go through k until an edge is found
			while(cube.get(k[i]) != 6) {
				i++;
				if((i)%4 == 0 && i!=1) {
					s++;
				}
			}
			
			i = k[i];
			//narrow down by side
			switch(s) {
				case 1: //yellow side
					switch(i) {
						case 1:
							crossHelper(new int[] {13}, new int[] {0, 10}, new int[] {1, 7}, new int[] {2, 4});
							break;
						case 3:
							crossHelper(new int[] {0, 13}, new int[] {1, 10}, new int[] {2, 7}, new int[] {4});
							break;
						case 4:
							crossHelper(new int[] {2, 13}, new int[] {10}, new int[] {0, 7}, new int[] {1, 4});
							break;
						case 6:
							crossHelper(new int[] {13, 1}, new int[] {2, 10}, new int[] {7}, new int[] {0, 4});
							break;
					}
					break;
				case 2: //orange side
					switch(i) {
						case 9:
							crossHelper(new int[] {5, 12, 3}, new int[] {2, 6, 11, 8}, new int[] {3, 8}, new int[] {2, 8, 3, 6});
							break;
						case 11:
							crossHelper(new int[] {12}, new int[] {15, 12, 17}, new int[] {16, 12, 16}, new int[] {17, 12, 15});
							break;
						case 12:
							crossHelper(new int[] {16, 8, 16}, new int[] {17, 8, 15}, new int[] {8}, new int[] {15, 8, 17});
							break;
						case 14:
							crossHelper(new int[] {3, 12}, new int[] {5, 17, 8, 15}, new int[] {5, 8}, new int[] {5, 15, 8, 17});
							break;
					}
					break;
				case 3: //blue side
					switch(i) {
						case 17:
							crossHelper(new int[] {16, 8, 17, 3}, new int[] {6, 11, 8}, new int[] {8, 17, 3}, new int[] {8, 3, 6});
							break;
						case 19:
							crossHelper(new int[] {15, 3}, new int[] {16, 3}, new int[] {17, 3}, new int[] {3});
							break;
						case 20:
							crossHelper(new int[] {17, 11}, new int[] {11}, new int[] {15, 11}, new int[] {16, 11});
							break;
						case 22:
							crossHelper(new int[] {8, 17, 11}, new int[] {8, 11}, new int[] {8, 15, 11}, new int[] {6, 3});
							break;
					}
					break;
				case 4: //red side
					switch(i) {
						case 25:
							crossHelper(new int[] {9, 14, 11}, new int[] {0, 6, 11, 8}, new int[] {11, 6}, new int[] {0, 8, 3, 6});
							break;
						case 27:
							crossHelper(new int[] {16, 6, 16}, new int[] {17, 6, 15}, new int[] {6}, new int[] {15, 6, 17});
							break;
						case 28:
							crossHelper(new int[] {14}, new int[] {15, 14, 17}, new int[] {16, 14, 16}, new int[] {17, 14, 15});
							break;
						case 30:
							crossHelper( new int[] {11, 15}, new int[] {9, 17, 6, 15}, new int[] {9, 6}, new int[] {15, 6, 17});
							break;
				}
				break;
				case 5: //green side
					switch(i) {
						case 33:
							crossHelper(new int[] {12, 15, 5}, new int[] {14, 9, 12}, new int[] {16, 12, 15, 5}, new int[] {12, 3, 14});
							break;
						case 35:
							crossHelper(new int[] {17, 9}, new int[] {9}, new int[] {15, 9}, new int[] {16, 9});
							break;
						case 36:
							crossHelper(new int[] {15, 5}, new int[] {16, 5}, new int[] {17, 5}, new int[] {5});
							break;
						case 38:
							crossHelper(new int[] {12, 17, 9}, new int[] {12, 9}, new int[] {12, 16, 14}, new int[] {14, 5});
							break;
					}
					break;
			}
		}
		
		
		//align the edges to centers
		while(cube.get(14) != 2 || cube.get(22) != 3 ||
				cube.get(30) != 4 || cube.get(38) != 5) {
			//orange
			if(cube.get(14) != 2) {
				crossHelper(22, new int[] {4, 2, 7, 0, 4}, 30, new int[] {4, 1, 10, 1, 4}, new int[] {4, 0, 13, 2, 4}, 2);
			}
			//blue
			if(cube.get(22) != 3) {
				crossHelper(14, new int[] {7, 0, 4, 2, 7}, 30, new int[] {7, 2, 10, 0, 7}, new int[] {7, 1, 13, 1, 7}, 3);
			}
			//red
			if(cube.get(30) != 4) {
				crossHelper(14, new int[] {10, 1, 4, 1, 10}, 22, new int[] {10, 0, 7, 2, 10}, new int[] {10, 2, 13, 0, 10}, 4);
			}
			//green
			if(cube.get(38) != 5) {
				crossHelper(14, new int[] {13, 2, 4, 0, 13}, 22, new int[] {13, 1, 7, 1, 13}, new int[] {13, 0, 10, 2, 13}, 5);
			}
		}
	}
	private void crossHelper(int[] b, int[] d, int[] f, int[] g) {
		if(cube.get(46) != 6) {
			for(int x : b) {
				turnSide(x);
			}
		} else if(cube.get(44) != 6) {
			for(int x : d) {
				turnSide(x);
			}
		} else if(cube.get(41) != 6) {
			for(int x : f) {
				turnSide(x);
			}
		} else {
			for(int x : g) {
				turnSide(x);
			}
		}
	}
	private void crossHelper(int a, int[] b, int c, int[] d, int[] f, int g) {
		if(cube.get(a) == g) {
			for(int x : b) {
				turnSide(x);
			}
		} else if(cube.get(c) == g) {
			for(int x : d) {
				turnSide(x);
			}
		} else {
			for(int x : f) {
				turnSide(x);
			}
		}
	}
	private void fl() {
		//System.out.println("OrangeBlue: ");
		int orangeBlue = flSearch(2, 3);
		int[] obMoves = {2, 1, -1, 0, 5, 0, 3, 2, 6, 2, 8, 40, 1, 2, -1, 0, 5, 0, 3, 0, 1, 2, -1, 6, 2, 8};
		while(orangeBlue != 40) {
			orangeBlue = flHelper(orangeBlue, obMoves);
		}
		//printCube();
		
		//System.out.println("RedBlue: ");
		int redBlue = flSearch(4, 3);
		int[] rbMoves = {1, 0, 2, -1, 9, 2, 11, 0, 8, 0, 6, 42, 0, 1, 2, -1, 8, 0, 6, -1, 0, 1, 2, 9, 2, 11};
		while(redBlue != 42) {
			redBlue = flHelper(redBlue, rbMoves);
		}
		//printCube();
		
		//System.out.println("RedGreen: ");
		int redGreen = flSearch(4, 5);
		int[] rgMoves = {0, -1, 1, 2, 12, 2, 14, 0, 11, 0, 9, 47, -1, 0, 1, 2, 11, 0, 9, 2, -1, 0, 1, 12, 2, 14};
		while(redGreen != 47) {
			redGreen = flHelper(redGreen, rgMoves);
		}
		//printCube();
		
		//System.out.println("OrangeGreen: ");
		int orangeGreen = flSearch(2, 5);
		int[] ogMoves = {-1, 2, 0, 1, 3, 2, 5, 0, 14, 0, 12, 45, 2, -1, 0, 1, 14, 0, 12, 1, 2, -1, 0, 3, 2, 5};
		while(orangeGreen != 45) {
			orangeGreen = flHelper(orangeGreen, ogMoves);
		}	
		//printCube();
	}
	//helper method for fl method
	private int flSearch(int x, int y) {
		int[][] corners = {{0, 8, 34}, {5, 10, 16}, {7, 18, 24}, {2, 26, 32},
				{40, 21, 15}, {42, 23, 29}, {47, 31, 37}, {45, 39, 13}};
		int corner = -1;
		int buffer;
		for(int i = 0; i < corners.length; i++) {
			buffer = cube.get(corners[i][0]);
			if(buffer == 6 || buffer == x || buffer == y) {
				buffer = cube.get(corners[i][1]);
				if(buffer == 6 || buffer == x || buffer == y) {
					buffer = cube.get(corners[i][2]);
					if(buffer == 6 || buffer == x || buffer == y) {
						if(cube.get(corners[i][0]) == 6) {
							corner = corners[i][0];
						} else if(cube.get(corners[i][1]) == 6) {
							corner = corners[i][1];
						} else {
							corner = corners[i][2];
						}
					}
				}
			}
		}
		return corner;
	}
	private int flHelper(int c, int[] moves) {
		if(c == 0 || c == 2 || c == 5 || c == 7) {
			if(c == 0) {
				turnSide(moves[0]);
			} else if(c == 2) {
				turnSide(moves[1]);
			} else if (c == 5) {
				turnSide(moves[2]);
			} else if (c == 7) {
				turnSide(moves[3]);
			}
			for(int i = 4; i < 11; i++) {
				turnSide(moves[i]);
			}
			c = moves[11];
			return c;
		} else if(c == 8 || c == 16 || c == 24 || c == 32) {
			if(c == 8) {
				turnSide(moves[12]);
			} else if(c == 16) {
				turnSide(moves[13]);
			} else if (c == 24) {
				turnSide(moves[14]);
			} else if (c == 32) {
				turnSide(moves[15]);
			}
			for(int i = 16; i < 19; i++) {
				turnSide(moves[i]);
			}
			c = moves[11];
			return c;
		} else if(c == 10 || c == 18 || c == 26 || c == 34) {
			if(c == 10) {
				turnSide(moves[19]);
			} else if(c == 18) {
				turnSide(moves[20]);
			} else if (c == 26) {
				turnSide(moves[21]);
			} else if (c == 34) {
				turnSide(moves[22]);
			}
			for(int i = 23; i < 26; i++) {
				turnSide(moves[i]);
			}
			c = moves[11];
			return c;
		} else if(c == 15 || c == 23 || c == 31 || c == 39) {
			if(c == 15) {
				turnSide(5);
				turnSide(2);
				turnSide(3);
				c = 18;
			} else if(c == 23) {
				turnSide(8);
				turnSide(2);
				turnSide(6);
				c = 26;
			} else if(c == 31) {
				turnSide(11);
				turnSide(2);
				turnSide(9);
				c = 34;
			} else {
				turnSide(14);
				turnSide(2);
				turnSide(12);
				c = 10;
			}
			return c;
		} else if(c == 13 || c == 21 || c == 29 || c == 37) {
			if(c == 13) {
				turnSide(3);
				turnSide(0);
				turnSide(5);
				c = 32;
			} else if(c == 21) {
				turnSide(6);
				turnSide(0);
				turnSide(8);
				c = 8;
			} else if(c == 29) {
				turnSide(9);
				turnSide(0);
				turnSide(11);
				c = 16;
			} else {
				turnSide(12);
				turnSide(0);
				turnSide(14);
				c = 24;
			}
		} else {
			if(c == 42) {
				turnSide(9);
				turnSide(0);
				turnSide(11);
				c = 10;
			} else if(c == 45) {
				turnSide(3);
				turnSide(0);
				turnSide(5);
				c = 26;
			} else { //c == 47
				turnSide(12);
				turnSide(0);
				turnSide(14);
				c = 18;
			}
		}
		return c;		
	}
	//edge find methods like with fl
	private static int[][] secondLayer = {{8, 5, 6, 0, 6, 2, 8, 3},	{6, 3, 11, 17, 6, 15, 5, 9, 8},
			{11, 8, 9, 0, 9, 2, 11, 6},	{14, 11, 12, 0, 12, 2, 14, 9},	{5, 14, 3, 0, 3, 2, 5, 12},	{9, 6, 14, 17, 9, 15, 8, 12, 11},	{12, 9, 5, 17, 12, 15, 11, 3, 14},	{3, 12, 8, 17, 3, 15, 14, 6, 5}, {}};
	private void sl() {
		int orangeBlue = slSearch(2, 3);
		int[] obMoves = {1, 0, -1, 2, 0, 12, -1, 0, 1, 2, 1, 12, 2, 1, 33};
		while(orangeBlue != 12) {
			orangeBlue = slHelper(orangeBlue, obMoves);
		}
		int redBlue = slSearch(4, 3);
		int[] rbMoves = {1, 0, -1, 2, 5, 27, 1, 2, -1, 0, 2, 27, 5, 33, 33};
		while(redBlue != 27) {
			redBlue = slHelper(redBlue, rbMoves);
		}
		int redGreen = slSearch(4, 5);
		int[] rgMoves = {-1, 2, 1, 0, 3, 28, 1, 2, -1, 0, 6, 28, 8, 1, 33};
		while(redGreen != 28) {
			redGreen = slHelper(redGreen, rgMoves);
		}
		int orangeGreen = slSearch(2, 5);
		int[] ogMoves = {-1, 2, 1, 0, 7, 11, -1, 0, 1, 2, 4, 11, 2, 1, 33};
		while(orangeGreen != 11) {
			orangeGreen = slHelper(orangeGreen, ogMoves);
		}
	}
	private int slSearch(int x, int y) {
		int[][] edges = {{1, 33}, {3, 9}, {6, 17}, {4, 25}, {11, 36}, {12, 19}, {20, 27}, {28, 35}};
		int buffer;
		int edge = -1;
		for(int i = 0; i < edges.length; i++) {
			buffer = cube.get(edges[i][0]);
			if(buffer == x || buffer == y) {
				buffer = cube.get(edges[i][1]);
				if(buffer == x || buffer == y) {
					if(cube.get(edges[i][0]) == x) {
						edge = edges[i][0];
					}else {
						edge = edges[i][1];
					}
				}
			}
		}
		return edge;
	}
	private int slHelper(int edge, int[] moves) {
		int ed = edge;
		if(ed == 1 || ed == 4 || ed == 6 || ed == 3) {
			if(ed == 1) {
				turnSide(moves[0]);
			} else if(ed == 4) {
				turnSide(moves[1]);
			} else if(ed == 6) {
				turnSide(moves[2]);
			} else if(ed == 3) {
				turnSide(moves[3]);
			}
			for(int i = 0; i < secondLayer[moves[4]].length; i++) {
				turnSide(secondLayer[moves[4]][i]);
			}
			ed = moves[5];
			return ed;
		} else if(ed == 9 || ed == 17 || ed == 25 || ed == 33) {
			if(ed == 9) {
				turnSide(moves[6]);
			} else if(ed == 17) {
				turnSide(moves[7]);
			} else if(ed == 25) {
				turnSide(moves[8]);
			} else if(ed == 33) {
				turnSide(moves[9]);
			}
			for(int i = 0; i < secondLayer[moves[10]].length; i++) {
				turnSide(secondLayer[moves[10]][i]);
			}
			ed = moves[11];
			return ed;
		}
		else if(ed == 20 || ed == 27) {
			for(int i = 0; i < secondLayer[moves[12]].length; i++) {
				turnSide(secondLayer[moves[12]][i]);
			}
			if(ed == 20) {
				ed = moves[13];
			} else {
				ed = moves[14];
			}
			return ed;
		} 
		else if(ed == 12 || ed == 19) {
			for(int i = 0; i < secondLayer[0].length; i++) {
				turnSide(secondLayer[0][i]);
			}
			if(ed == 12) {
				ed = 4;
			} else {
				ed = 25;
			}
			return ed;
		} else if(ed == 28 || ed == 35) {
			for(int i = 0; i < secondLayer[3].length; i++) {
				turnSide(secondLayer[3][i]);
			}
			if(ed == 28) {
				ed = 3;
			} else {
				ed = 9;
			}
			return ed;
		} else {
			for(int i = 0; i < secondLayer[4].length; i++) {
				turnSide(secondLayer[4][i]);
			}
			if(ed == 36) {
				ed = 6;
			} else {
				ed = 17;
			}
			return ed;
		}
	}
	static private int[][] OLL = {
			 	{9, 1, 10, 6, 9, 8, 1, 11, 6, 9, 8},
			 	{6, 9, 0, 11, 2, 8, 12, 0, 3, 2, 5, 14},
			 	{12, 0, 3, 2, 5, 14, 2, 6, 9, 0, 11, 2, 8},
			 	{12, 0, 3, 2, 5, 14, 0, 6, 9, 0, 11, 2, 8},
			 	{5, 13, 9, 12, 11, 12, 3},
			 	{3, 7, 11, 8, 9, 8, 5},
			 	{3, 6, 11, 6, 9, 7, 5},
			 	{5, 14, 9, 14, 11, 13, 3},
			 	{9, 0, 11, 2, 11, 6, 10, 0, 11, 2, 8},
			 	{9, 0, 11, 0, 11, 6, 9, 8, 9, 1, 11},
			 	{5, 10, 12, 11, 12, 9, 13, 11, 12, 11, 3}, 
			 	{3, 10, 8, 9, 8, 11, 7, 9, 8, 5, 9},
			 	{3, 8, 5, 2, 3, 6, 5, 8, 0, 6},
			 	{11, 6, 9, 0, 11, 8, 9, 6, 2, 8},
			 	{5, 14, 3, 11, 2, 9, 0, 5, 12, 3},
			 	{3, 6, 5, 9, 0, 11, 2, 3, 8, 5},
			 	{9, 0, 11, 0, 11, 6, 9, 8, 1, 11, 6, 9, 8},
			 	{0, 9, 1, 10, 6, 9, 8, 1, 3, 11, 6, 9, 8, 5},
			 	{5, 9, 12, 9, 12, 11, 14, 3, 10, 6, 9, 8},
			 	{9, 5, 12, 9, 12, 11, 14, 10, 4, 6, 9, 8, 5},
			 	{9, 1, 11, 2, 9, 0, 11, 2, 9, 2, 11}, 
			 	{9, 1, 10, 2, 10, 2, 10, 1, 9},
			 	{10, 15, 11, 1, 9, 17, 11, 1, 11},
			 	{3, 6, 11, 8, 5, 6, 9, 8},
			 	{0, 8, 3, 6, 11, 8, 5, 6, 9},
			 	{9, 1, 11, 2, 9, 2, 11},
			 	{9, 0, 11, 0, 9, 1, 11},
			 	{3, 6, 11, 8, 5, 9, 0, 9, 2, 11},
			 	{0, 9, 0, 11, 2, 9, 2, 11, 8, 2, 6, 9, 0, 11},
			 	{2, 6, 0, 9, 1, 11, 2, 9, 1, 11, 2, 8},
			 	{11, 2, 6, 0, 9, 2, 11, 8, 9},
			 	{9, 0, 14, 2, 11, 0, 9, 12, 11},
			 	{9, 0, 11, 2, 11, 6, 9, 8},
			 	{9, 0, 10, 2, 11, 6, 9, 0, 9, 2, 8},
			 	{9, 1, 10, 6, 9, 8, 9, 1, 11},
			 	{11, 2, 9, 2, 11, 0, 9, 0, 9, 14, 11, 12},
			 	{6, 9, 2, 11, 2, 9, 0, 11, 8},
			 	{9, 0, 11, 0, 9, 2, 11, 2, 11, 6, 9, 8},
			 	{3, 8, 5, 2, 3, 0, 6, 2, 5},
			 	{11, 6, 9, 0, 11, 2, 8, 0, 9},
			 	{9, 0, 11, 0, 9, 1, 11, 6, 9, 0, 11, 2, 8},
			 	{11, 2, 9, 2, 11, 1, 9, 6, 9, 0, 11, 2, 8},
			 	{0, 11, 2, 8, 0, 6, 9},
			 	{12, 0, 3, 2, 5, 14},
			 	{6, 9, 0, 11, 2, 8},
			 	{11, 2, 11, 6, 9, 8, 0, 9},
			 	{8, 5, 2, 3, 0, 5, 2, 3, 0, 6},
			 	{6, 9, 0, 11, 2, 9, 0, 11, 2, 8},
			 	{3, 8, 4, 12, 4, 6, 4, 14, 3},
			 	{5, 12, 4, 8, 4, 14, 4, 6, 5},
			 	{12, 0, 3, 2, 5, 0, 3, 2, 5, 14},
			 	{11, 2, 9, 2, 11, 0, 8, 0, 6, 9, 2},
			 	{5, 14, 9, 14, 11, 12, 9, 14, 11, 13, 3},
			 	{3, 6, 11, 6, 9, 8, 11, 6, 9, 7, 5},
			 	{0, 11, 6, 9, 0, 9, 2, 10, 8, 10, 2, 11, 0, 9, 0, 11},
			 	{5, 14, 3, 2, 11, 0, 9, 2, 11, 0, 9, 5, 12, 3},
			 	{9, 0, 11, 2, 3, 11, 6, 9, 8, 5},
		};
	//NEEDS TO BE MORE EFFICIENT -- replace if/else with binary decision tree
	private void oll() {
		//determine how many yellow on face
		int count = 0;
		for(int i = 0; i < 8; i++) {
			if(cube.get(i) == 1) {
				count++;
			}
		}
		int[] r = {1, 1};
		switch(count) {
			case 0:
				//two dot algorithms
				while(cube.get(8) != 1 || cube.get(10) != 1) {
					turnSide(0);
				}
				if(cube.get(24) == 1 && cube.get(26) == 1) {
					r = OLL[0];
				} else {
					r = OLL[1];
				}
				break;
			case 1:
				while(cube.get(2) != 1) {
					turnSide(0);
				}
				if(cube.get(24) == 1) {
					r = OLL[3];
				} else {
					turnSide(0);
					r = OLL[2];
				}
				break;
			case 2:
				//if all edges are wrong 
				if(cube.get(1) != 1 && cube.get(4) != 1 && 
				cube.get(6) != 1 && cube.get(3) != 1) {
					while(cube.get(0) != 1) {
						turnSide(0);
					}
					if(cube.get(7) == 1) {
						if(cube.get(16) == 1) {
							turnSide(1);
						}
						r = OLL[16];
					} else if(cube.get(2) != 1) {
						turnSide(0);
						if(cube.get(16) == 1) {
							r = OLL[17];
						} else {
							r = OLL[18];
						}
					} else {
						if(cube.get(16) == 1) {
							r = OLL[17];
						} else {
							r = OLL[18];
						}
					}
				}
				else {
					//edges (56, 52, 55, 51, 50, 54, 47, 49, 53, 48)
					while(cube.get(1) != 1) {
						turnSide(0);
					}
					//lines
					if(cube.get(6) == 1) {
						if(cube.get(16) == 1 && cube.get(18) == 1
								&& cube.get(32) == 1 && cube.get(34) == 1) {
							turnSide(0);
							r = OLL[55];
						} else if(cube.get(8) == 1 && cube.get(10) == 1
								&& cube.get(24) == 1 && cube.get(26) == 1) {
							r = OLL[54];
						} else {
							while(cube.get(34) != 1) {
								turnSide(1);
							}
							if(cube.get(26) == 1) {
								r = OLL[51];
							} else {
								turnSide(2);
								r = OLL[50];
							}
						}
					}
					//corners
					else {
						//put all corners on top right
						if(cube.get(3) == 1) {
							turnSide(0);
						}
						
						//right side full
						if(cube.get(8) == 1 && cube.get(9) == 1 && cube.get(10) == 1) {
							if(cube.get(18) == 1) {
								r = OLL[48];
							}  else {
								r = OLL[53];
							}
						} //full bottom row
						else if(cube.get(16) == 1 && cube.get(17) == 1 && cube.get(18) == 1) {
							if(cube.get(8) == 1) {
								turnSide(0);
								r = OLL[49];
							} else {
								turnSide(0);
								r = OLL[52];
							}
						} //last two corners
						else {
							if(cube.get(10) == 1) {
								turnSide(2);
								r = OLL[47];
							} else {
								r = OLL[46];
							}
						}
					}
				}
				break;
			case 3:
				while(cube.get(7) != 1) {
					turnSide(0);
				}
				
				if(cube.get(3) == 1 && cube.get(4) == 1) {
					if(cube.get(34) == 1) {
						r = OLL[14];
					} else {
						r = OLL[13];
					}
				} else if(cube.get(1) == 1 && cube.get(6) == 1) {
					if(cube.get(26) == 1) {
						turnSide(0);
						r = OLL[12];
					} else {
						turnSide(2);
						r = OLL[15];
					}
				} else if(cube.get(4) == 1 && cube.get(6) == 1) {
					if(cube.get(16) == 1) {
						turnSide(2);
						r = OLL[5];
					} else {
						r = OLL[4];
					}
				} else if(cube.get(1) == 1 && cube.get(3) == 1) {
					if(cube.get(26) == 1) {
						turnSide(2);
						r = OLL[9];
					} else {
						r = OLL[8];
					}
				} else if(cube.get(3) == 1 && cube.get(6) == 1) {
					if(cube.get(34) == 1) {
						turnSide(0);
						r = OLL[6];
					} else {
						turnSide(1);
						r = OLL[11];
					}
				} else {
					if(cube.get(10) == 1) {
						turnSide(0);
						r = OLL[10];
					} else {
						turnSide(1);
						r = OLL[7];
					}
				}
				break;
			case 4:
				//checkered board
				if(cube.get(0) == 1 && cube.get(2) == 1 &&
						cube.get(5) == 1 && cube.get(7) == 1) {
					r = OLL[19];
				}//cross
				else if(cube.get(0) != 1 && cube.get(2) != 1 &&
						cube.get(5) != 1 && cube.get(7) != 1) {
					while(cube.get(32) != 1 || cube.get(18) != 1) {
						turnSide(0);
					}
					if(cube.get(34) == 1) {
						r = OLL[20];
					} else {
						r = OLL[21];
					}
				//narrow down remaining possibilities
				} else {
					while(cube.get(7) != 1) {
						turnSide(0);
					}
					//diagonal corners
					if(cube.get(0) == 1) {
						if(cube.get(1) == 1 && cube.get(6) == 1) {
							if(cube.get(10) == 1) {
								turnSide(0);
								r = OLL[38];
							} else {
								turnSide(2);
								r = OLL[38];
							}
						} else if(cube.get(3) == 1 && cube.get(4) == 1) {
							if(cube.get(16) == 1) {
								turnSide(1);
								r = OLL[39];
							} else {
								r = OLL[39];
							}
						}
						else if(cube.get(1) == 1 && cube.get(3) == 1) {
							if(cube.get(16) == 1) {
								r = OLL[36];
							}else {
								turnSide(1);
								r = OLL[34];
							}
						}
						else if(cube.get(4) == 1 && cube.get(6) == 1) {
							if(cube.get(32) == 1) {
								turnSide(1);
								r = OLL[36];
							} else {
								r = OLL[34];
							}
						}
						else if(cube.get(4) == 1 && cube.get(1) == 1) {
							if(cube.get(10) == 1) {
								turnSide(1);
								r = OLL[35];
							}else {
								turnSide(2);
								r = OLL[37];
							}
						}
						else {
							if(cube.get(10) == 1) {
								turnSide(0);
								r = OLL[37];
							} else {
								r = OLL[35];
							}
						}
					}
					//2 corners are on the same side
					else {
						if(cube.get(2) != 1) {
							turnSide(2);
						}
						
						//full bar on right side
						if(cube.get(4) == 1) {
							if(cube.get(3) == 1) {
								if(cube.get(8) == 1) {
									r = OLL[44];
								} else {
									r = OLL[32];
								}
							} else if(cube.get(1) == 1) {
								if(cube.get(16) == 1) {
									r = OLL[30];
								} else {
									turnSide(1);
									r = OLL[42];
								}
							} else {
								if(cube.get(10) == 1) {
									r = OLL[43];
								} else {
									r = OLL[31];
								}
							}
						}
						//full bar in center
						else if(cube.get(1) == 1 && cube.get(6) == 1) {
							if(cube.get(10) == 1) {
								turnSide(1);
								r = OLL[45];
							} else {
								turnSide(0);
								r = OLL[33];
							}
						}
						else if(cube.get(1) == 1 && cube.get(3) == 1) {
							if(cube.get(10) == 1) {
								turnSide(2);
								r = OLL[41];
							} else {
								turnSide(2);
								r = OLL[28];
							}
						}
						else {
							if(cube.get(10) == 1) {
								turnSide(0);
								r = OLL[40];
							} else {
								turnSide(1);
								r = OLL[29];
							}
						}
					}
				}
				break;
			case 5:
				while(cube.get(2) != 1) {
					turnSide(0);
				}
				if(cube.get(34) != 1) {
					r = OLL[25];
				}else {
					turnSide(1);
					r = OLL[26];
				}
				break;
			case 6:
				if(cube.get(1) == 1 && cube.get(4) == 1 && 
					cube.get(6) == 1 && cube.get(3) == 1) {
					//corners wrong
					while(cube.get(5) != 1) {
						turnSide(0);
					}
					if(cube.get(0) != 1 && cube.get(7) != 1) {
						while(cube.get(16) != 1) {
							turnSide(0);
						}
						r = OLL[24];
					} else {
						if(cube.get(0) != 1) {
							turnSide(0);
						}
						if(cube.get(18) == 1) {
							turnSide(1);
							r = OLL[23];
						} else {
							turnSide(0);
							r = OLL[22];
						}
					}
				} else {
					//edges wrong
					while(cube.get(6) == 1) {
						turnSide(0);
					}
					if(cube.get(1) == 1) {
						if(cube.get(4) != 1) {
							r = OLL[27];
						} else {
							turnSide(2);
							r = OLL[27];
						}
					}else {
						r = OLL[56];
					}
				}
				break;
		}
		//implement algorithm
		for(int i = 0; i < r.length; i++) {
			turnSide(r[i]);
		}
	}
	static private int[][] PLL = {
				{10, 0, 9, 0, 11, 2, 11, 2, 11, 0, 11},
				{9, 2, 9, 0, 9, 0, 9, 2, 11, 2, 10},
				{10, 4, 15, 10, 4, 0, 11, 3, 7, 10, 4, 13, 11, 3, 1},
				{10, 4, 15, 10, 4, 1, 10, 4, 15, 10, 4},
				{11, 6, 11, 13, 9, 8, 11, 13, 10},
				{10, 13, 9, 6, 11, 13, 9, 8, 9},
				{9, 14, 11, 6, 9, 12, 11, 8, 9, 12, 11, 6, 9, 14, 11, 8},
				{9, 2, 11, 2, 9, 0, 9, 15, 11, 2, 9, 17, 11, 1, 11, 2},
				{11, 1, 9, 1, 11, 6, 9, 0, 11, 2, 11, 8, 10, 2},
				{11, 0, 5, 1, 9, 2, 11, 1, 9, 3, 2},
				{9, 0, 11, 8, 9, 0, 11, 2, 11, 6, 10, 2, 11, 2},
				{9, 0, 11, 2, 11, 6, 10, 2, 11, 2, 9, 0, 11, 8},
				{11, 2, 8, 9, 0, 11, 2, 11, 6, 10, 2, 11, 2, 9, 0, 11, 0, 9},
				{11, 0, 11, 2, 14, 11, 13, 2, 14, 0, 14, 9, 12, 9},
				{6, 9, 2, 11, 2, 9, 0, 11, 8, 9, 0, 11, 2, 11, 6, 9, 8},
				{9, 0, 11, 0, 9, 0, 11, 8, 9, 0, 11, 2, 11, 6, 10, 2, 11, 1, 9, 2, 11},
				{11, 0, 9, 2, 11, 8, 2, 6, 9, 0, 11, 6, 11, 8, 9, 2, 9},
				{10, 0, 11, 0, 11, 2, 9, 2, 10, 15, 2, 11, 0, 9, 17, 0},
				{8, 2, 6, 10, 15, 14, 0, 12, 2, 12, 17, 10},
				{10, 2, 9, 2, 9, 0, 11, 0, 10, 17, 0, 9, 2, 11, 15, 2},
				{17, 9, 0, 11, 2, 15, 10, 2, 9, 2, 11, 0, 11, 0, 10, 0}
		};
	//NEEDS TO BE MORE EFFICIENT -- get rid of excessive if/else
	private void pll() {
		int[] r = {0, 0, 0, 0};

		for(int i = 0; i < 4; i++) {
			//1 or 2 - whole line is solved and corners across
			if(cube.get(8) == 2 && cube.get(9) == 2 && cube.get(10) == 2 &&
					cube.get(16) == 3 && cube.get(34) == 5 && cube.get(18) == 3 &&
					cube.get(24) == 4 && cube.get(26) == 4 && cube.get(32) == 5) {
				if(cube.get(17) == 5) {
					turnSide(0);
					r = PLL[0];
					break;
				} else if(cube.get(17) == 4){
					turnSide(0);
					r = PLL[1];
					break;
				}
			} else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(18) == 3 &&
					cube.get(10) == 2 && cube.get(24) == 4 && cube.get(8) == 2 &&
					cube.get(34) == 5 && cube.get(26) == 4 && cube.get(32) == 5) {
				if(cube.get(25) == 2) {
					turnSide(1);
					r = PLL[0];
					break;
				} else if(cube.get(25) == 5){
					turnSide(1);
					r = PLL[1];
					break;
				}
			} else if(cube.get(24) == 4 && cube.get(25) == 4 && cube.get(26) == 4 &&
					cube.get(18) == 3 && cube.get(32) == 5 && cube.get(8) == 2 &&
					cube.get(34) == 5 && cube.get(10) == 2 && cube.get(16) == 3) {
				if(cube.get(17) == 2) {
					turnSide(2);
					r = PLL[0];
					break;
				} else if(cube.get(17) == 5){
					turnSide(2);
					r = PLL[1];
					break;
				}
			} else if(cube.get(32) == 5 && cube.get(33) == 5 && cube.get(34) == 5 &&
					cube.get(26) == 4 && cube.get(8) == 2 && cube.get(18) == 3 &&
					cube.get(24) == 4 && cube.get(10) == 2 && cube.get(16) == 3) {
				if(cube.get(17) == 2) {
					r = PLL[0];
					break;
				} else if(cube.get(17) == 4){
					r = PLL[1];
					break;
				}
			}
			
			
			//3 - all 4 corners are solved and edges are adjacent
			else if(cube.get(8) == 2 && cube.get(10) == 2 && cube.get(16) == 3 && cube.get(18) == 3 &&
					cube.get(24) == 4 && cube.get(26) == 4 && cube.get(32) == 5 && cube.get(34) == 5 &&
					cube.get(9) == 5 && cube.get(33) == 2){
				r = PLL[2];
				break;
			} else if(cube.get(8) == 2 && cube.get(10) == 2 && cube.get(16) == 3 && cube.get(18) == 3 &&
					cube.get(24) == 4 && cube.get(26) == 4 && cube.get(32) == 5 && cube.get(34) == 5 &&
					cube.get(9) == 3 && cube.get(17) == 2){
				turnSide(0);
				r = PLL[2];
				break;
			}
			
			
			//4 - all corners solved and edges make plus
			else if(cube.get(8) == 2 && cube.get(10) == 2 && cube.get(16) == 3 && cube.get(18) == 3 &&
					cube.get(24) == 4 && cube.get(26) == 4 && cube.get(32) == 5 && cube.get(34) == 5 &&
					cube.get(9) == 4 && cube.get(25) == 2 && cube.get(17) == 5 && cube.get(33) == 3){
				r = PLL[3];
				break;
			}
			
			
			//5 and 6 - one corner solved and all edges solved
			//bottom left solved
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					cube.get(10) == 2 && cube.get(16) == 3){
				if(cube.get(18) == 2 || cube.get(24) == 2) {
					r = PLL[4];
					break;
				} else {
					r = PLL[5];
					break;
				}
			} //bottom right solved
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					cube.get(18) == 3 && cube.get(24) == 4){
				if(cube.get(26) == 3 || cube.get(32) == 3) {
					turnSide(0);
					r = PLL[4];
					break;
				} else {
					turnSide(0);
					r = PLL[5];
					break;
				}
			}//top right solved
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					cube.get(26) == 4 && cube.get(32) == 5){
				if(cube.get(34) == 4 || cube.get(8) == 4) {
					turnSide(1);
					r = PLL[4];
					break;
				} else {
					turnSide(1);
					r = PLL[5];
					break;
				}
			}//top left solved
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					cube.get(8) == 2 && cube.get(34) == 5){
				if(cube.get(10) == 5 || cube.get(16) == 5) {
					turnSide(2);
					r = PLL[4];
					break;
				} else {
					turnSide(2);
					r = PLL[5];
					break;
				}
			}
			
			
			//7 - all edges solved and corners go vertical or horozontal
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					(cube.get(10) == 2 || cube.get(10) == 5) && (cube.get(16) == 2 || cube.get(16) == 5) &&
					(cube.get(18) == 4 || cube.get(18) == 5) && (cube.get(24) == 4 || cube.get(24) == 5)){
				r = PLL[6];
				break;
			} else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(25) == 4 && cube.get(33) == 5 &&
					(cube.get(10) == 3 || cube.get(10) == 4) && (cube.get(16) == 3 || cube.get(16) == 4) &&
					(cube.get(18) == 2 || cube.get(18) == 3) && (cube.get(24) == 2 || cube.get(24) == 3)){
				turnSide(0);
				r = PLL[6];
				break;
			}
			
			//8
			else if(cube.get(10) == 2 && cube.get(16) == 3 && cube.get(17) == 3 && 
					cube.get(8) == 2 && cube.get(34) == 5 && cube.get(25) == 4) {
				r = PLL[7];
				break;
			} else if(cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4 && 
					cube.get(10) == 2 && cube.get(16) == 3 && cube.get(33) == 5) {
				turnSide(0);
				r = PLL[7];
				break;
			} else if(cube.get(32) == 5 && cube.get(26) == 4 && cube.get(33) == 5 && 
					cube.get(18) == 3 && cube.get(24) == 4 && cube.get(9) == 2) {
				turnSide(1);
				r = PLL[7];
				break;
			} else if(cube.get(17) == 3 && cube.get(32) == 5 && cube.get(26) == 4 && 
					cube.get(34) == 5 && cube.get(8) == 2 && cube.get(9) == 2) {
				turnSide(2);
				r = PLL[7];
				break;
			}
			
			
			//9
			else if(cube.get(10) == 2 && cube.get(16) == 3 && cube.get(9) == 2 && 
					cube.get(33) == 5 && cube.get(18) == 3 && cube.get(24) == 4) {
				r = PLL[8];
				break;
			} else if(cube.get(18) == 3 && cube.get(24) == 4 && cube.get(17) == 3 && 
					cube.get(32) == 5 && cube.get(26) == 4 && cube.get(9) == 2) {
				turnSide(0);
				r = PLL[8];
				break;
			} else if(cube.get(32) == 5 && cube.get(26) == 4 && cube.get(25) == 4 && 
					cube.get(17) == 3 && cube.get(34) == 5 && cube.get(8) == 2) {
				turnSide(1);
				r = PLL[8];
				break;
			} else if(cube.get(10) == 2 && cube.get(16) == 3 && cube.get(8) == 2 && 
					cube.get(34) == 5 && cube.get(33) == 5 && cube.get(25) == 4) {
				turnSide(2);
				r = PLL[8];
				break;
			}
			
			
			//10 - whole line solved and right edge
			else if(cube.get(8) == 2 && cube.get(9) == 2 && cube.get(10) == 2 &&
					cube.get(17) == 3) {
				turnSide(2);
				r = PLL[9];
				break;
			} else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(18) == 3 &&
					cube.get(25) == 4) {
				r = PLL[9];
				break;
			} else if(cube.get(24) == 4 && cube.get(25) == 4 && cube.get(26) == 4 &&
					cube.get(33) == 5) {
				turnSide(0);
				r = PLL[9];
				break;
			} else if(cube.get(32) == 5 && cube.get(33) == 5 && cube.get(34) == 5 &&
					cube.get(9) == 2) {
				turnSide(1);
				r = PLL[9];
				break;
			}
			
			
			//11 - whole line solved and left edge
			else if(cube.get(8) == 2 && cube.get(9) == 2 && cube.get(10) == 2 &&
					cube.get(33) == 5) {
				r = PLL[10];
				break;
			} else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(18) == 3 &&
					cube.get(9) == 2) {
				turnSide(0);
				r = PLL[10];
				break;
			} else if(cube.get(24) == 4 && cube.get(25) == 4 && cube.get(26) == 4 &&
					cube.get(17) == 3) {
				turnSide(1);
				r = PLL[10];
				break;
			} else if(cube.get(32) == 5 && cube.get(33) == 5 && cube.get(34) == 5 &&
					cube.get(25) == 4) {
				turnSide(2);
				r = PLL[10];
				break;
			}
			
			
			//12 - corner and edge solved across from each other
			else if(cube.get(10) == 2 && cube.get(16) == 3 && cube.get(17) == 3 && 
					cube.get(8) == 2 && cube.get(34) == 5 && cube.get(33) == 5){
					r = PLL[11];
					break;
			} else if(cube.get(10) == 2 && cube.get(16) == 3 && cube.get(9) == 2 && 
					cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4){
					turnSide(0);
					r = PLL[11];
					break;
			} else if(cube.get(18) == 3 && cube.get(24) == 4 && cube.get(17) == 3 && 
					cube.get(26) == 4 && cube.get(32) == 5 && cube.get(33) == 5){
					turnSide(1);
					r = PLL[11];
					break;
			} else if(cube.get(32) == 5 && cube.get(26) == 4 && cube.get(25) == 4 && 
					cube.get(34) == 5 && cube.get(8) == 2 && cube.get(9) == 2){
					turnSide(2);
					r = PLL[11];
					break;
			}
			
			
			//13 whole side solved and edge across
			if(cube.get(8) == 2 && cube.get(9) == 2 && cube.get(10) == 2 &&
					cube.get(25) == 4 && cube.get(17) == 5) {
				r = PLL[12];
				break;
			} else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(18) == 3 &&
					cube.get(33) == 5 && cube.get(25) == 2) {
				turnSide(0);
				r = PLL[12];
				break;
			} else if(cube.get(24) == 4 && cube.get(25) == 4 && cube.get(26) == 4 &&
					cube.get(9) == 2 && cube.get(17) == 5) {
				turnSide(1);
				r = PLL[12];
				break;
			} else if(cube.get(32) == 5 && cube.get(33) == 5 && cube.get(34) == 5 &&
					cube.get(17) == 3 && cube.get(25) == 2) {
				turnSide(2);
				r = PLL[12];
				break;
			}
			
			
			//14 - whole corner solved and corner diagonal solved
			//bottom left solved
			else if(cube.get(9) == 2 && cube.get(17) == 3 && cube.get(10) == 2 && cube.get(16) == 3 &&
					cube.get(26) == 4 && cube.get(32) == 5 && cube.get(33) == 4 && cube.get(25) == 5){
				r = PLL[13];
				break;
			} //bottom right solved
			else if(cube.get(17) == 3 && cube.get(25) == 4 && cube.get(18) == 3 && cube.get(24) == 4 &&
					cube.get(8) == 2 && cube.get(34) == 5 && cube.get(9) == 5 && cube.get(33) == 2){
				turnSide(0);
				r = PLL[13];
				break;
			}//top right solved
			else if(cube.get(25) == 4 && cube.get(33) == 5 && cube.get(26) == 4 && cube.get(32) == 5 &&
					cube.get(10) == 2 && cube.get(16) == 3 && cube.get(9) == 3 && cube.get(17) == 2){
				turnSide(1);
				r = PLL[13];
				break;
			}//top left solved
			else if(cube.get(9) == 2 && cube.get(33) == 5 && cube.get(8) == 2 && cube.get(34) == 5
					 && cube.get(18) == 3 && cube.get(24) == 4 && cube.get(17) == 4 && cube.get(25) == 3){
				turnSide(2);
				r = PLL[13];
				break;
			}
			
			
			//15 - two adjacent edges solved
			else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(10) == 2 && 
					cube.get(25) == 4 && cube.get(26) == 4 && cube.get(32) == 5
					&& cube.get(9) == 5 && cube.get(33) == 2){
				r = PLL[14];
				break;
			} else if(cube.get(8) == 2 && cube.get(34) == 5 && cube.get(33) == 5 && 
					cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4
					&& cube.get(9) == 3 && cube.get(17) == 2){
				turnSide(0);
				r = PLL[14];
				break;
			} else if(cube.get(9) == 2 && cube.get(16) == 3 && cube.get(10) == 2 && 
					cube.get(33) == 5 && cube.get(26) == 4 && cube.get(32) == 5
					&& cube.get(17) == 4 && cube.get(25) == 3){
				turnSide(1);
				r = PLL[14];
				break;
			} else if(cube.get(18) == 3 && cube.get(17) == 3 && cube.get(24) == 4 && 
					cube.get(9) == 2 && cube.get(8) == 2 && cube.get(34) == 5
					&& cube.get(33) == 4 && cube.get(25) == 5){
				turnSide(2);
				r = PLL[14];
				break;
			}
			
			
			//16
			 else if(cube.get(18) == 3 && cube.get(17) == 3 && cube.get(24) == 4 && 
						cube.get(33) == 5 && cube.get(8) == 2 && cube.get(34) == 5
						&& cube.get(9) == 4 && cube.get(25) == 2){
				r = PLL[15];
				break;
			} else if(cube.get(16) == 3 && cube.get(9) == 2 && cube.get(10) == 2 && 
					cube.get(25) == 4 && cube.get(26) == 4 && cube.get(32) == 5
					&& cube.get(17) == 5 && cube.get(33) == 3){
				turnSide(0);
				r = PLL[15];
				break;
			}
			
			
			//17
			else if(cube.get(16) == 3 && cube.get(17) == 3 && cube.get(10) == 2 && 
					cube.get(33) == 5 && cube.get(26) == 4 && cube.get(32) == 5
					&& cube.get(9) == 4 && cube.get(24) == 2){
				r = PLL[16];
				break;
			} else if(cube.get(8) == 2 && cube.get(34) == 5 && cube.get(9) == 2 && 
					cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4
					&& cube.get(17) == 5 && cube.get(33) == 3){
				turnSide(0);
				r = PLL[16];
				break;
			}
			
			//18 edges then corners
			else if(cube.get(9) == 4 && cube.get(25) == 5 && cube.get(33) == 2 &&
					(cube.get(10) == 2 || cube.get(10) == 5) && (cube.get(16) == 2 || cube.get(16) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 5) && (cube.get(34) == 4 || cube.get(34) == 5)
					&& (cube.get(26) == 2 || cube.get(26) == 3) && (cube.get(32) == 2 || cube.get(32) == 3)
					&& cube.get(17) == 3 && cube.get(18) == 3 && cube.get(24) == 4) {
				r = PLL[17];
				break;
			} else if(cube.get(9) == 3 && cube.get(17) == 5 && cube.get(33) == 2 &&
					(cube.get(10) == 2 || cube.get(10) == 5) && (cube.get(16) == 2 || cube.get(16) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 3) && (cube.get(34) == 4 || cube.get(34) == 3)
					&& (cube.get(18) == 2 || cube.get(18) == 3) && (cube.get(18) == 2 || cube.get(24) == 3)
					&& cube.get(32) == 5 && cube.get(25) == 4 && cube.get(26) == 4) {
				turnSide(0);
				r = PLL[17];
				break;
			} else if(cube.get(9) == 3 && cube.get(25) == 2 && cube.get(17) == 4 &&
					(cube.get(10) == 4 || cube.get(10) == 5) && (cube.get(16) == 4 || cube.get(16) == 5)
					&& (cube.get(18) == 2 || cube.get(18) == 3) && (cube.get(24) == 2 || cube.get(24) == 3)
					&& (cube.get(26) == 4 || cube.get(26) == 3) && (cube.get(32) == 4 || cube.get(32) == 3)
					&& cube.get(8) == 2 && cube.get(33) == 5 && cube.get(34) == 5) {
				turnSide(1);
				r = PLL[17];
				break;
			} else if(cube.get(17) == 4 && cube.get(25) == 5 && cube.get(33) == 3 &&
					(cube.get(18) == 2 || cube.get(18) == 5) && (cube.get(24) == 2 || cube.get(24) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 5) && (cube.get(34) == 4 || cube.get(34) == 5)
					&& (cube.get(26) == 4 || cube.get(26) == 3) && (cube.get(32) == 4 || cube.get(32) == 3)
					&& cube.get(9) == 2 && cube.get(10) == 2 && cube.get(16) == 3) {
				turnSide(2);
				r = PLL[17];
				break;
			}
			
			//19 edges then corners
			else if(cube.get(9) == 5 && cube.get(25) == 2 && cube.get(33) == 4 &&
					(cube.get(10) == 4 || cube.get(10) == 5) && (cube.get(16) == 4 || cube.get(16) == 5)
					&& (cube.get(8) == 2 || cube.get(8) == 3) && (cube.get(34) == 2 || cube.get(34) == 3)
					&& (cube.get(26) == 2 || cube.get(26) == 5) && (cube.get(32) == 2 || cube.get(32) == 5)
					&& cube.get(17) == 3 && cube.get(18) == 3 && cube.get(24) == 4) {
				r = PLL[18];
				break;
			} else if(cube.get(9) == 5 && cube.get(17) == 2 && cube.get(33) == 3 &&
					(cube.get(10) == 3 || cube.get(10) == 4) && (cube.get(16) == 3 || cube.get(16) == 4)
					&& (cube.get(8) == 2 || cube.get(8) == 3) && (cube.get(34) == 2 || cube.get(34) == 3)
					&& (cube.get(18) == 2 || cube.get(18) == 5) && (cube.get(24) == 2 || cube.get(24) == 5)
					&& cube.get(32) == 5 && cube.get(25) == 4 && cube.get(26) == 4) {
				turnSide(0);
				r = PLL[18];
				break;
			} else if(cube.get(9) == 4 && cube.get(25) == 3 && cube.get(17) == 2 &&
					(cube.get(10) == 4 || cube.get(10) == 3) && (cube.get(16) == 4 || cube.get(16) == 3)
					&& (cube.get(18) == 4 || cube.get(18) == 5) && (cube.get(24) == 4 || cube.get(34) == 5)
					&& (cube.get(26) == 2 || cube.get(26) == 3) && (cube.get(32) == 2 || cube.get(32) == 3)
					&& cube.get(8) == 2 && cube.get(33) == 5 && cube.get(34) == 5) {
				turnSide(1);
				r = PLL[18];
				break;
			} else if(cube.get(17) == 5 && cube.get(25) == 3 && cube.get(33) == 4 &&
					(cube.get(18) == 4 || cube.get(18) == 5) && (cube.get(18) == 4 || cube.get(24) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 3) && (cube.get(34) == 4 || cube.get(34) == 3)
					&& (cube.get(26) == 5 || cube.get(26) == 2) && (cube.get(32) == 5 || cube.get(32) == 2)
					&& cube.get(9) == 2 && cube.get(10) == 2 && cube.get(16) == 3) {
				turnSide(2);
				r = PLL[18];
				break;
			}
			
			//20 edges then corners
			else if(cube.get(9) == 4 && cube.get(25) == 3 && cube.get(17) == 2 &&
					(cube.get(10) == 3 || cube.get(10) == 4) && (cube.get(16) == 3 || cube.get(16) == 4)
					&& (cube.get(8) == 2 || cube.get(8) == 3) && (cube.get(34) == 2 || cube.get(34) == 3)
					&& (cube.get(18) == 2 || cube.get(18) == 5) && (cube.get(24) == 2 || cube.get(24) == 5)
					&& cube.get(33) == 5 && cube.get(32) == 5 && cube.get(26) == 4) {
				r = PLL[19];
				break;
			} else if(cube.get(17) == 5 && cube.get(25) == 3 && cube.get(33) == 4 &&
					(cube.get(10) == 4 || cube.get(10) == 3) && (cube.get(16) == 4 || cube.get(16) == 3)
					&& (cube.get(18) == 4 || cube.get(18) == 5) && (cube.get(24) == 4 || cube.get(24) == 5)
					&& (cube.get(26) == 2 || cube.get(26) == 3) && (cube.get(32) == 2 || cube.get(32) == 3)
					&& cube.get(9) == 2 && cube.get(8) == 2 && cube.get(34) == 5) {
				turnSide(0);
				r = PLL[19];
				break;
			} else if(cube.get(9) == 5 && cube.get(25) == 2 && cube.get(33) == 4 &&
					(cube.get(18) == 4 || cube.get(18) == 5) && (cube.get(18) == 4 || cube.get(24) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 3) && (cube.get(34) == 4 || cube.get(34) == 3)
					&& (cube.get(26) == 5 || cube.get(26) == 2) && (cube.get(32) == 5 || cube.get(32) == 2)
					&& cube.get(10) == 2 && cube.get(16) == 3 && cube.get(17) == 3){
				turnSide(1);
				r = PLL[19];
				break;
			} else if(cube.get(9) == 5 && cube.get(17) == 2 && cube.get(33) == 3 &&
					(cube.get(10) == 4 || cube.get(10) == 5) && (cube.get(16) == 4 || cube.get(16) == 5)
					&& (cube.get(8) == 2 || cube.get(8) == 3) && (cube.get(34) == 2 || cube.get(34) == 3)
					&& (cube.get(26) == 2 || cube.get(26) == 5) && (cube.get(32) == 2 || cube.get(32) == 5)
					&& cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4) {
				turnSide(2);
				r = PLL[19];
				break;
			}

			//21 edges then corners
			else if(cube.get(9) == 3 && cube.get(17) == 5 && cube.get(33) == 2 &&
					(cube.get(10) == 2 || cube.get(10) == 5) && (cube.get(16) == 2 || cube.get(16) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 5) && (cube.get(34) == 4 || cube.get(34) == 5)
					&& (cube.get(26) == 2 || cube.get(26) == 3) && (cube.get(32) == 2 || cube.get(32) == 3)
					&& cube.get(18) == 3 && cube.get(24) == 4 && cube.get(25) == 4) {
				r = PLL[20];
				break;
			} else if(cube.get(9) == 3 && cube.get(25) == 2 && cube.get(17) == 4 &&
					(cube.get(10) == 2 || cube.get(10) == 5) && (cube.get(16) == 2 || cube.get(16) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 3) && (cube.get(34) == 4 || cube.get(34) == 3)
					&& (cube.get(18) == 2 || cube.get(18) == 3) && (cube.get(18) == 2 || cube.get(24) == 3)
					&& cube.get(26) == 4 && cube.get(32) == 5 && cube.get(33) == 5) {
				turnSide(0);
				r = PLL[20];
				break;
			} else if(cube.get(17) == 4 && cube.get(25) == 5 && cube.get(33) == 3 &&
					(cube.get(10) == 4 || cube.get(10) == 5) && (cube.get(16) == 4 || cube.get(16) == 5)
					&& (cube.get(18) == 2 || cube.get(18) == 3) && (cube.get(24) == 2 || cube.get(24) == 3)
					&& (cube.get(26) == 4 || cube.get(26) == 3) && (cube.get(32) == 4 || cube.get(32) == 3)
					&& cube.get(8) == 2 && cube.get(9) == 2 && cube.get(34) == 5){
				turnSide(1);
				r = PLL[20];
				break;
			} else if(cube.get(9) == 4 && cube.get(25) == 5 && cube.get(33) == 2 &&
					(cube.get(18) == 2 || cube.get(18) == 5) && (cube.get(24) == 2 || cube.get(24) == 5)
					&& (cube.get(8) == 4 || cube.get(8) == 5) && (cube.get(34) == 4 || cube.get(34) == 5)
					&& (cube.get(26) == 4 || cube.get(26) == 3) && (cube.get(32) == 4 || cube.get(32) == 3)
					&& cube.get(10) == 2 && cube.get(16) == 3 && cube.get(17) == 3) {
				turnSide(2);
				r = PLL[20];
				break;
			}
			
			turnSide(0);
		}//for loop
	
		//implement algorithm
		for(int i = 0; i < r.length; i++) {
			turnSide(r[i]);
		}
		
		//turn up side until solved
		for(int j = 0; j < 4; j++) {
			if(cube.get(9) != 2) {
				turnSide(0);
			}
		}
	}
	private void configureSolution() {		
		for(int i = 0; i <= 15; i += 3) {
			for(int k = 0; k < 3; k++) {
				for(int j = 0; j < solution.size()-1; j++) {
					if(solution.get(j) == i && solution.get(j+1) == i) {
						solution.remove(j);
						solution.set(j, i+1);
					}
					if(j < solution.size()-1 && solution.get(j) == i+2 && solution.get(j+1) == i+2) {
						solution.remove(j);
						solution.set(j, i+1);
					}
					if(j < solution.size()-1 && solution.get(j) == i+1 && solution.get(j+1) == i+1) {
						solution.remove(j);
						solution.remove(j);
					}
					if(j < solution.size()-1 && solution.get(j) == i && solution.get(j+1) == i+2 ||
							j < solution.size()-1 && solution.get(j) == i+2 && solution.get(j+1) == i){
						solution.remove(j);
						solution.remove(j);
					}
					if(j < solution.size()-1 && solution.get(j) == i && solution.get(j+1) == i+1 ||
							j < solution.size()-1 && solution.get(j) == i+1 && solution.get(j+1) == i) {
						solution.remove(j);
						solution.set(j, i+2);
					}
					if(j < solution.size()-1 && solution.get(j) == i+1 && solution.get(j+1) == i+2 ||
							j < solution.size()-1 && solution.get(j) == i+2 && solution.get(j+1) == i+1) {
						solution.remove(j);
						solution.set(j, i);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		Cube c = new Cube();
		c.shuffle();
		c.solve();
		ArrayList<Integer> shuffle = c.shuffle;
		ArrayList<Integer> solution = c.solution;
	}
}
