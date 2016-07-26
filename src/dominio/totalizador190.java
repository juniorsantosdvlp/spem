package dominio;

import java.util.ArrayList;

public class totalizador190{

	ArrayList<C190> listaC190 = new ArrayList<>();
	ArrayList<String> stringC190 = new ArrayList<>();
	
	public void lerC170(String[] c170){
		C190 c190 = new C190();
		c190.campo01 = "C190";
		c190.campo02 = Double.parseDouble(c170[9]);
		c190.campo03 = Double.parseDouble(c170[10]);
		c190.campo04 = Double.parseDouble(c170[14]);
		c190.campo05 = Double.parseDouble(c170[6]); //vlr operação
		c190.campo06 = Double.parseDouble(c170[12]); //base icms
		c190.campo07 = Double.parseDouble(c170[14]); // valor icms
		c190.campo08 = Double.parseDouble(c170[15]);//bc ST_icms
		c190.campo09 = Double.parseDouble(c170[17]);
		c190.campo10 = 0.0;
		c190.campo11 = Double.parseDouble(c170[23]);
		c190.campo12 = "";
	//	if(this.listaC190.contains(c190)){
		//	listaC190.get(listaC190.indexOf(c190)[1] =+ Double.parseDouble(c170[2]));
		//}
		
		
	}
	
	
	
private class C190 {

	public String campo01;
	private Double campo02;
	private Double campo03;
	private Double campo04;
	private Double campo05;
	private Double campo06;
	private Double campo07;
	private Double campo08;
	private Double campo09;
	private Double campo10;
	private Double campo11;
	private String campo12;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campo02 == null) ? 0 : campo02.hashCode());
		result = prime * result + ((campo03 == null) ? 0 : campo03.hashCode());
		result = prime * result + ((campo04 == null) ? 0 : campo04.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		C190 other = (C190) obj;
		if (campo02 == null) {
			if (other.campo02 != null)
				return false;
		} else if (!campo02.equals(other.campo02))
			return false;
		if (campo03 == null) {
			if (other.campo03 != null)
				return false;
		} else if (!campo03.equals(other.campo03))
			return false;
		if (campo04 == null) {
			if (other.campo04 != null)
				return false;
		} else if (!campo04.equals(other.campo04))
			return false;
		return true;
	}

}
}