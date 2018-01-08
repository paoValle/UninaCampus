import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private final String OUTPUT_FOLDER = ".";
	private List<String> output = new ArrayList<>();
	private String[] annotations = {"//Activity", "//Dialog", "//AlertDialog"};

	public static void main(String[] args) throws IOException {
		if (args.length==0) {
			System.out.println("Errore: path del file non indicato. Indicare il path del file come primo parametro.");
		} else {
			System.out.println("File di test: "+args[0]);
			Main app = new Main();
			app._start(args[0]);
		}

	}
	
	public void _start(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("Il file di input non esiste.");
			System.exit(-1);			
		} else {
			System.out.println("Il file di input esiste.");
		}

		List<String> allLines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
		/* STRATEGIA:
		 * 	1) Arrivo fino al metodo contrassegnato da @Test;
		 * 	2) Inizio a leggere righe e le pusho nella lista "output";
		 * 	3) Quando trovo un "R.id", dato che è l'unico modo che abbiamo per tradurre in
		 * 		robolectric, torno indietro fino a inizio istruzione e chiedo all'utente di
		 * 		cosa si tratta. In base alla risposta inserisco una riga con l'annotazione in
		 * 		"output". Dopo di che salto fino al primo ; e continuo dal punto 2.
		 * 
		 */
		int j = 0;
		//scorro fino a @Test inserendo ogni riga in "output"
		while (allLines.size()<j && !allLines.get(j).equals("@Test")) {
			output.add(allLines.get(j++));
		}
		//vedo se ha trovato @Test
		if (j == allLines.size()) {
			System.out.println("Il file di input non contiene alcun metodo di test.");
			System.exit(-2);				
		}
		//inizio con la strategia
		while (j < allLines.size()) {
			String line = allLines.get(j);
			output.add(line);
			if (line.contains("R.id.")) {
				/* "R.id." presente, scorro fino a "inizio istruzione", riconoscendo una riga
				 * bianca (nel qual caso sommo 1 per avere l'inizio istruzone giusto) oppure
				 * un uguale
				 */
				int start = j;
				while (!line.contains("=") && !line.equals("")) {
					start--;
					line = allLines.get(start);
				}
				//se sono uscito perche ho trovato riga bianca, sposto start avanti di 1
				if (line.equals(""))
					start++;
				//porto avanti j fino ad un ";" per trovare l'istruzione intera
				j++;
				while (!allLines.get(j).contains(";") && j<allLines.size()) {
					output.add(allLines.get(j++));					
				}
				//stampo l'istruzione all utente e chiedo come annotarla
				System.out.println("Istruzione trovata: (start="+start+",end="+j+")");
				printLines(allLines, start, j);
				System.out.println("Premere: 0 Activity - 1 Dialog - 2 AlertDialog");
				int scelta = chiediScelta();
				//inserisco annotazione in "output" in posizione start
				output.add(start, annotations[scelta]);
				
			}
			j++;			
		}
		writeLinesOnFile(path.replace(".java",  "_annotato.java"), output, 0 , output.size());
		System.out.println("File di output creato: "+path.replace(".java",  "_annotato.java"));
	}
	
	private void printLines(List<String> l, int s, int e) {
		for (int i = s; i<=e && i<l.size(); i++) {
			System.out.println(l.get(i));
		}
	}
	
	private int chiediScelta() throws IOException{
		int scelta=-1;
		do{
			System.out.println("Scelta: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String temp;
			temp = br.readLine();
			scelta = Integer.parseInt(temp);
			if(!(scelta==0 || scelta ==1 || scelta==2)){
				System.out.println("Attenzione, scegliere 0,1 oppure 2");
			}
		} while(!(scelta==0 || scelta ==1 || scelta==2));
		return scelta;
	}

	private void writeLinesOnFile(String name, List<String> l, int s, int e) {
		FileWriter w = null;
		try {
			w = new FileWriter(name);		
			for (String line : l) {
				w.write(line+"\n");
			}
			w.close();
		} catch (IOException e1) {
			System.out.println("Errore nello scrivere il file di output.");
			System.exit(-3);
		}
	}
}
