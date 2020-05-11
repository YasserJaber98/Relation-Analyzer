import java.io.*;
import java.util.*;

public class RelationsAnalyzer {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream instream = new FileInputStream("sample.txt");
		Scanner x = new Scanner(instream);
		int Size = x.nextInt();
		String[] elem = new String[Size]; 
		for (int i = 0; i < Size; i++) {
			elem[i] = x.next();
		}
		int mtrx[][] = new int[Size][Size];
		while (x.hasNext()) {
			while (!x.hasNextInt())
				x.next();
			for (int i = 0; i < Size; i++) {
				for (int j = 0; j < Size; j++) {
					mtrx[i][j] = x.nextInt();

				}
			}

		}
		for (int i = 0; i < Size; i++) {
			for (int j = 0; j < Size; j++) {
				System.out.print(mtrx[i][j] + "   ");
			}
			System.out.println();
		}
		System.out.println("The input relation is:");
		System.out.print("Reflexivity  -----> ");
		if (isReflexive(mtrx))
			System.out.println("Reflexive");
		if (isIrreflexive(mtrx))
			System.out.println("Irreflexive");
		if (!isReflexive(mtrx) && !isIrreflexive(mtrx))
			System.out.println("Neither reflexive nor irreflexive");
		System.out.print("Symmetry     -----> ");
		if (isSymmatric(mtrx))
			System.out.println("Symmatric");
		if (isAntiSymmatric(mtrx))
			System.out.println("AntiSymmatric");
		if (isASymmatric(mtrx))
			System.out.print("ASymmatric");
		if (!isSymmatric(mtrx) && !isAntiSymmatric(mtrx) && !isASymmatric(mtrx))
			System.out.println("Neither symmatric, nor antisymmatric, nor asymatric");
		System.out.print("Transitivity -----> ");
		if (isTransitive(mtrx))
			System.out.println("Transitive");
		else
			System.out.println("Not transitive");
		if (isReflexive(mtrx) && isAntiSymmatric(mtrx) && isTransitive(mtrx)) {
			System.out.println("Hence, the relation is a partial order");
			int min = Size + 1;
			int index = 0;
			ArrayList<Integer> done = new ArrayList<Integer>();
			ArrayList<checker> hassDaigram = new ArrayList<checker>();
			for (int i = 0; i < Size; i++) {
				min += mtrx[i][0];
			}
			int sum = 0;

			for (int k = 0; k < Size; k++) {
				for (int i = 0; i < Size; i++) {
					sum += mtrx[i][k];
				}
				if (sum < min) {
					min = sum;
					index = k;
				}
				sum = 0;
			}

			done.add(index);
			hassDaigram.add(new checker(index, "P" + hassDaigram.size()));
			int remain = Size - 1;
			boolean hasParent = false;
			boolean relation = false;

			while (remain > 0) {
				min = Size + 1;
				for (int k = 0; k < Size; k++) {
					if (!(done.contains(k))) {

						sum = 0;
						for (int i = 0; i < Size; i++) {
							sum += mtrx[i][k];
						}

						if (sum < min) {
							min = sum;
							index = k;
						}
					}
				}
				hassDaigram.add(new checker(index, "P" + hassDaigram.size()));
				for (int i = hassDaigram.size() - 2; i > -1; i--) {
					for (int z = 0; z < Size; z++) {
						if (mtrx[z][hassDaigram.get(i).getIndex()] == 1
								&& mtrx[z][hassDaigram.get(hassDaigram.size() - 1).getIndex()] == 1) {
							relation = true;
						}
						if (mtrx[z][hassDaigram.get(i).getIndex()] == 1
								&& mtrx[z][hassDaigram.get(hassDaigram.size() - 1).getIndex()] == 0) {
							relation = false;
							break;
						}
					}
					if (relation == true) {
						for (int q = 0; q < hassDaigram.get(hassDaigram.size() - 1).getChild().size(); q++) {
							for (int w = 0; w < hassDaigram.get(i).getParent().size(); w++) {

								if (hassDaigram.get(hassDaigram.size() - 1).getChild().get(q).getIndex() == hassDaigram
										.get(i).getParent().get(w).getIndex()) {
									hasParent = true;
									break;
								}
							}
							if (hasParent) {
								break;
							}
						}
						if (!hasParent) {
							hassDaigram.get(hassDaigram.size() - 1).setChild(hassDaigram.get(i));
							hassDaigram.get(i).setParent(hassDaigram.get(hassDaigram.size() - 1));
							for (int r = 0; r < hassDaigram.get(i).getChild().size(); r++) {
								hassDaigram.get(i).getChild().get(r).setParent(hassDaigram.get(hassDaigram.size() - 1));
							}
						}
					}
					relation = false;
					hasParent = false;
				}
				done.add(index);
				--remain;

			}

			min = elem.length;
			for (int i = 0; i < hassDaigram.size(); i++) {
				System.out.print(hassDaigram.get(i).getName() + " (");
				if (hassDaigram.get(i).getChild().size() > 0) {
					for (int k = hassDaigram.get(i).getChild().size() - 1; k >= 0; k--) {
						System.out.print(hassDaigram.get(i).getChild().get(k).getName() + ",");
					}
				} else {
					System.out.print(" - ,");
				}
				System.out.println("{" + elem[hassDaigram.get(i).getIndex()] + "})");
			}

			ArrayList<checker> maximal = new ArrayList<checker>();
			ArrayList<checker> minimal = new ArrayList<checker>();
			for (int i = 0; i < hassDaigram.size(); i++) {
				if (hassDaigram.get(i).getParent().size() == 0) {
					maximal.add(hassDaigram.get(i));
				}
				if (hassDaigram.get(i).getChild().size() == 0) {
					minimal.add(hassDaigram.get(i));
				}
			}

			checker greatest = null;
			checker least = null;
			if (maximal.size() == 1) {
				greatest = maximal.get(0);
			}
			if (minimal.size() == 1) {
				least = minimal.get(0);
			}
			System.out.print("Maximal Element/s : { ");
			for (int i = 0; i < maximal.size() - 1; i++) {
				System.out.print(elem[maximal.get(i).getIndex()] + ", ");
			}
			System.out.println(elem[maximal.get(maximal.size() - 1).getIndex()] + " }");
			if (greatest != null) {
				System.out.println("Greatest Element : { " + elem[greatest.getIndex()] + " }");
			} else {
				System.out.println("Greatest Element : None");
			}
			System.out.print("Minimal Element/s : { ");
			for (int i = 0; i < minimal.size() - 1; i++) {
				System.out.print(elem[minimal.get(i).getIndex()] + ", ");
			}
			System.out.println(elem[minimal.get(minimal.size() - 1).getIndex()] + " }");
			if (least != null) {
				System.out.println("Least Element : { " + elem[least.getIndex()] + " }");
			} else {
				System.out.println("Least Element : None");
			}
		} else if (isReflexive(mtrx) && isSymmatric(mtrx) && isTransitive(mtrx)) {
			System.out.println("Hence, the relation is an equivalence relation");

			String classes = "";
			ArrayList EqClasses = new ArrayList();
			for (int i = 0; i < mtrx.length; i++) {
				for (int j = 0; j < mtrx.length; j++) {
					if (mtrx[i][j] == 1) {
						classes += elem[j] + ",";
					}
				}
				if (!EqClasses.contains(classes)) {
					EqClasses.add(classes);
				}
				classes = "";
			}
			for (int i = 0; i < EqClasses.size(); i++) {
				String EqvClasses = ((String) EqClasses.get(i)).substring(0, ((String) EqClasses.get(i)).length() - 1);
				System.out.println("Equicalence class # " + (i + 1) + " is  {" + EqvClasses + "}");
			}
		}

		else
			System.out.println("Hence, the relation is not an equivalence relation, nor a partial order");

	}

	public static boolean isReflexive(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i][i] == 0)
				return false;
		}
		return true;
	}

	public static boolean isIrreflexive(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i][i] == 1)
				return false;
		}
		return true;
	}

	public static boolean isSymmatric(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++)
				if (arr[i][j] != arr[j][i])
					return false;
		}
		return true;
	}

	public static boolean isAntiSymmatric(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++)
				if (arr[i][j] == arr[j][i])
					return false;
		}
		return true;
	}

	public static boolean isASymmatric(int[][] arr) {
		if (isAntiSymmatric(arr) && isIrreflexive(arr))
			return true;
		return false;
	}

	public static boolean isTransitive(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] == 1) {
					for (int k = 0; k < arr.length; k++) {
						if (arr[j][k] == 1) {
							if (arr[i][k] == 0)
								return false;
						}
					}
				}
			}
		}
		return true;

	}

}
