package comm;

public class Comm {

	public static int[] stringArrayToIntArray(String[] ageStrGroup) {
		int[] res = new int[ageStrGroup.length];
		for (int i = 0; i < ageStrGroup.length; i++) {
			String temp = ageStrGroup[i];
			res[i] = Integer.parseInt(temp);
		}
		return res;
	}

	public static void main(String[] args) {
		String[] testData = {"3","4","5"};
		int[] res = stringArrayToIntArray(testData);
		for (int i = 0; i < res.length; i++) {
			int j = res[i];
			System.out.println(j);
		}
		
	}
}
