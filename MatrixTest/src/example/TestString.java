package example;

public class TestString {

	public static void main(String[] args) {
		
		// строки - это неизменяемый объкт
		
		String s1 = "2ddfgdg";
		String s2 = "2ddfgdg";

		System.out.println(s1 == s2); // должно быть false
		System.out.println(s1.equals(s2));
		
		String s3 = new String("2ddfgdg");
		String s4 = new String("2ddfgdg");
		
		System.out.println(s3 == s4); // точно false
		System.out.println(s3.equals(s4));
		
		// создается объект строки на основании полей старой
		// а старая остается неизменной
		s1 = s1 + "1"; //конкатенация
		
		String s5 = "6";
		
		for(int i = 0; i < 100; i++) {
			String s6 = "6";
			s5 = s6;
		}
		
		

	}

}
