package prova;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class TestEspresso {


	public static void main(String[] args) {
		
		String activity = "NewActivity activity;"+"\n"+"assertNotNull(shadowOf(RuntimeEnvironment.application));"+"\n"+"assertTrue(Robolectric.setupActivity(NewActivity.class) != null);"+"\n"+"activity = Robolectric.setupActivity(NewActivity.class);";
		
		String clickActivity = "(activity.findViewById(ID)).performClick();";
		String clickDialog = "(ShadowDialog.getLatestDialog().findViewById(ID)).performClick();";
		String clickAlertDialog = "(ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).performClick();";
		
		String longClickActivity = "(activity.findViewById(ID)).performLongClick();";
		String longClickDialog = "(ShadowDialog.getLatestDialog().findViewById(ID)).performLongClick();";
		String longClickAlertDialog =  "(ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).performLongClick();";
		
		String testoDialog = "assertEquals(TestoDaTestare,((TextView)ShadowDialog.getLatestDialog().findViewById(ID)).getText());";
		String testoActivity = "assertEquals(TestoDaTestare,((TextView)activity.findViewById(ID)).getText().toString());";
		String testoAlertDialog = " assertEquals(TestoDaTestare,((TextView)ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).getText());";
		
		String isCheckedActivity = "assertTrue(((CheckBox) activity.findViewById(ID)).isChecked());";
		String isCheckedDialog = "assertTrue(((CheckBox) ShadowDialog.getLatestDialog().findViewById(ID)).isChecked());";
		String isCheckedAlertDialog = "assertTrue(((CheckBox) ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).isChecked());";
		
		String isDisplayedActivity = "assertNotNull(activity.findViewById(ID));";
		String isDisplayedDialog = "assertNotNull(ShadowDialog.getLatestDialog().findViewById(ID));";
		String isDisplayedAlertDialog = "assertNotNull(ShadowAlertDialog.getLatestAlertDialog().findViewById(ID));";
		
		String inputTextActivity = "((TextView) activity.findViewById(ID)).setText(TestoDaTestare);";
		String inputTextDialog = "((TextInputEditText)ShadowDialog.getLatestDialog().findViewById(ID)).setText(TestoDaTestare);";
		String inputTextAlertDialog = "((EditText)ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).setText(TestoDaTestare);";
		
		String selectItemSpinnerActivity = "((Spinner) activity.findViewById(ID)).setSelection(Posizione);";
		String selectItemSpinnerDialog = "((Spinner) ShadowDialog.getLatestDialog().findViewById(ID)).setSelection(Posizione);";
		String selectItemSpinnerAlertDialog = "((Spinner) ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).setSelection(Posizione);";

		String clickItemListaActivity = "shadowOf((ListView) activity.findViewById(ID)).performItemClick(Posizione);";
		String clickItemListaDialog = "shadowOf((ListView) ShadowDialog.getLatestDialog().findViewById(ID)).performItemClick(Posizione);";
		String clickItemListaAlertDialog = "shadowOf((ListView) ShadowAlertDialog.getLatestAlertDialog().findViewById(ID)).performItemClick(Posizione);";

		boolean inizioTest = false;
		boolean idTrovato = false;
		String id = null;
		String pos = null;
		String rif = null;
		List<Evento> eventi = new ArrayList<Evento>();
		File file = new File("C:/Users/antonio/Desktop/ProvaTestTradotto.java");
		String lineaActivity = null;		
		try(FileWriter fw = new FileWriter(file);){

			List<String> allLines = Files.readAllLines(Paths.get("C:/Users/antonio/Desktop/ProvaPerTraduttore_annotato.java"));
			List<String> righeId = new ArrayList<String>();
			 
			for (String line : allLines) {
				
				//Prendo l'activity da utilizzare
				if(line.contains("@Rule")){
					lineaActivity = allLines.get(allLines.indexOf(line)+1);
					lineaActivity = lineaActivity.substring(28);
					lineaActivity = lineaActivity.substring(0,lineaActivity.indexOf(">"));
					//System.out.println(lineaActivity);
				}

				//Leggo il test suite - Condizioni inizio e fine
				if(line.contains("@Test"))
					inizioTest = true;
				
				if(line.contains("}"))
					inizioTest = false;
				
				//Lettura eventi
				if(inizioTest){
					if(!line.contains("{") && !idTrovato){
						righeId.add(line);
						if(line.contains(";")){
							id = searchId(righeId);
							pos = searchPos(righeId);
							rif = searchRif(righeId);
							righeId.clear();
							idTrovato = true;
						}
					}else{
						if(line.contains(";")){
							searchAction(line,eventi,id,pos,rif);
							idTrovato = false;
						}
					}
				}
			
			}		
			//Eventi trovati
			printEventi(eventi);
				
		
		//Scrivo il nuovo file robolectric
			
	        List<String> allLines2 = Files.readAllLines(Paths.get("C:/Users/antonio/Desktop/RobolectricTest.java"));
			
			for (String line2 : allLines2) {
				
				if(line2.contains("@Test")){
					int indiceInserimento = allLines2.indexOf(line2) + 2 ;
					allLines2.add(indiceInserimento,activity.replace("NewActivity", lineaActivity));
					indiceInserimento = indiceInserimento + 1 ;
					for(Evento e : eventi){
						//int scelta = -1;
						//System.out.println( e);
						//System.out.println("Premere: 0 Activity - 1 Dialog - 2 AlertDialog");
						//scelta = chiediScelta();
						if(e.idElemento != null){
						
						if(e.action.contains("click")){
							switch(e.rif){
							case "Activity":
								if(e.pos== null)
									allLines2.add(indiceInserimento,clickActivity.replace("ID", e.idElemento));
								else
									allLines2.add(indiceInserimento,clickItemListaActivity.replace("ID", e.idElemento).replace("Posizione", e.pos));
								break;
							case "Dialog":
								if(e.pos == null)
									allLines2.add(indiceInserimento,clickDialog.replace("ID", e.idElemento));
								else
									allLines2.add(indiceInserimento,clickItemListaDialog.replace("ID", e.idElemento).replace("Posizione", e.pos));
								break;
							case "AlertDialog":
								if(e.pos == null)
									allLines2.add(indiceInserimento,clickAlertDialog.replace("ID", e.idElemento));
								else
									allLines2.add(indiceInserimento,clickItemListaAlertDialog.replace("ID", e.idElemento).replace("Posizione", e.pos));
								break;
							}	
						}
						if(e.action.contains("longClick")){
							switch(e.rif){
							case "Activity":
								allLines2.add(indiceInserimento,longClickActivity.replace("ID", e.idElemento));
								break;
							case "Dialog":
								allLines2.add(indiceInserimento,longClickDialog.replace("ID", e.idElemento));
								break;
							case "AlertDialog":
								allLines2.add(indiceInserimento,longClickAlertDialog.replace("ID", e.idElemento));
								break;
							}
						}
						if(e.action.contains("matches(withText(")){
							String text = e.action.substring(e.action.indexOf("Text(")+5);
							
							switch(e.rif){
							case "Activity":
								allLines2.add(indiceInserimento,testoActivity.replace("ID", e.idElemento).replace("TestoDaTestare",text));
								break;
							case "Dialog":
								allLines2.add(indiceInserimento,testoDialog.replace("ID", e.idElemento).replace("TestoDaTestare",text));
								break;
							case "AlertDialog":
								allLines2.add(indiceInserimento,testoAlertDialog.replace("ID", e.idElemento).replace("TestoDaTestare",text));
								break;
							}
						}
						if(e.action.contains("isChecked")){
							switch(e.rif){
							case "Activity":
								allLines2.add(indiceInserimento,isCheckedActivity.replace("ID", e.idElemento));
								break;
							case "Dialog":
								allLines2.add(indiceInserimento,isCheckedDialog.replace("ID", e.idElemento));
								break;
							case "AlertDialog":
								allLines2.add(indiceInserimento,isCheckedAlertDialog.replace("ID", e.idElemento));
								break;
							}
						}
						if(e.action.contains("isDisplayed")){
							switch(e.rif){
							case "Activity":
								allLines2.add(indiceInserimento,isDisplayedActivity.replace("ID", e.idElemento));
								break;
							case "Dialog":
								allLines2.add(indiceInserimento,isDisplayedDialog.replace("ID", e.idElemento));
								break;
							case "AlertDialog":
								allLines2.add(indiceInserimento,isDisplayedAlertDialog.replace("ID", e.idElemento));
								break;
							}
						}
						if(e.action.contains("replaceText(")){
							String text = e.action.substring(e.action.indexOf("Text(")+5);
							switch(e.rif){
							case "Activity":
								allLines2.add(indiceInserimento,inputTextActivity.replace("ID", e.idElemento).replace("TestoDaTestare",""+text+""));
								break;
							case "Dialog":
								allLines2.add(indiceInserimento,inputTextDialog.replace("ID", e.idElemento).replace("TestoDaTestare",text));
								break;
							case "AlertDialog":
								allLines2.add(indiceInserimento,inputTextAlertDialog.replace("ID", e.idElemento).replace("TestoDaTestare",text));
								break;
							}
						}
						}else{
							//Caso selezione elemento di uno spinner
							if(e.action.contains("click")){
								switch(e.rif){
								case "Activity":
									allLines2.add(indiceInserimento,selectItemSpinnerActivity.replace("ID", eventi.get(eventi.indexOf(e)-1).idElemento).replace("Posizione", e.pos));
									break;
								case "Dialog":
									allLines2.add(indiceInserimento,selectItemSpinnerDialog.replace("ID", eventi.get(eventi.indexOf(e)-1).idElemento).replace("Posizione", e.pos));
									break;
								case "AlertDialog":
									allLines2.add(indiceInserimento,selectItemSpinnerAlertDialog.replace("ID", eventi.get(eventi.indexOf(e)-1).idElemento).replace("Posizione", e.pos));
									break;
								}
							}
						}
						indiceInserimento = indiceInserimento + 1;
					}
					break;
				}
			}
			
			 
	        
	        
			for(String s : allLines2){
				fw.write(s + "\n");
			}
			
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String searchId(List<String> righeId){
		String id = null;
		for(String line : righeId){
			if(line.contains("allOf(withId(")){
				line = line.substring(line.indexOf("withId("));
				id= line.substring(7,line.indexOf(")"));
			}
		}
		return id;
	}
	
	public static String searchPos(List<String> righeId){
		String pos = null;
		for(String line : righeId){
			if(line.contains("atPosition")){
				pos = line.substring(line.indexOf("(")+1,line.indexOf(")"));
			}
		}
		return pos;
	}
	
	public static String searchRif(List<String> righeId){
		String rif = null;
		for(String line : righeId){
			if(line.contains("//")){
				rif = line.substring(line.indexOf("//")+2);
			}
		}
		return rif;
	}

	public static void searchAction(String line, List<Evento> eventi , String id, String pos, String rif){
		String item = null;
		String action = null;
		
		item = line.substring(0,line.indexOf("."));
	//	action = line.substring(line.indexOf("(")+1,line.indexOf(")")-1);
		action = line.substring(line.indexOf("(")+1,line.indexOf(")"));
		eventi.add(new Evento(id,item,action,pos,rif));
	}
	
	public static void printEventi(List<Evento> eventi){
		for(Evento e : eventi)
			System.out.println(e);
	}
	
	/*public static int chiediScelta() throws IOException{
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
	}*/
}
