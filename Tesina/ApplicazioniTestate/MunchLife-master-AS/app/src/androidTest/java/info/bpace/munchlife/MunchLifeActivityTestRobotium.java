package info.bpace.munchlife;

import info.bpace.munchlife.MunchLifeActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class MunchLifeActivityTestRobotium extends ActivityInstrumentationTestCase2<MunchLifeActivity> {
  	private Solo solo;
  	
  	public MunchLifeActivityTestRobotium() {
		super(MunchLifeActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
  		// Wait for activity: 'info.bpace.munchlife.MunchLifeActivity'
		solo.waitForActivity(MunchLifeActivity.class, 2000);
		// Sleep for 14431 milliseconds
		solo.sleep(2000);//14431);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 3391 milliseconds
		solo.sleep(2000);//3391);
		// Click on Remove Gear
		solo.clickOnView(solo.getView(R.id.down_gear_button));
		// Sleep for 3563 milliseconds
		solo.sleep(2000);//3563);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1624 milliseconds
		solo.sleep(2000);//1624);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1516 milliseconds
		solo.sleep(2000);//1516);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1461 milliseconds
		solo.sleep(2000);//1461);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1429 milliseconds
		solo.sleep(2000);//1429);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1513 milliseconds
		solo.sleep(2000);//1513);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1503 milliseconds
		solo.sleep(2000);//1503);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1841 milliseconds
		solo.sleep(2000);//1841);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1615 milliseconds
		solo.sleep(2000);//1615);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Wait for dialog
		solo.waitForDialogToOpen(20000);
		// Sleep for 4293 milliseconds
		solo.sleep(2000);//4293);
		// Click on Okay
		solo.clickOnView(solo.getView(android.R.id.button3));
		// Sleep for 4419 milliseconds
		solo.sleep(2000);//4419);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 2140 milliseconds
		solo.sleep(2000);//2140);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1983 milliseconds
		solo.sleep(2000);//1983);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1793 milliseconds
		solo.sleep(2000);//1793);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 2711 milliseconds
		solo.sleep(2000);//2711);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 3155 milliseconds
		solo.sleep(2000);//3155);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 2604 milliseconds
		solo.sleep(2000);//2604);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 3043 milliseconds
		solo.sleep(2000);//3043);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 2724 milliseconds
		solo.sleep(2000);//2724);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 2434 milliseconds
		solo.sleep(2000);//2434);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 2809 milliseconds
		solo.sleep(2000);//2809);
		// Click on Okay
		solo.clickOnView(solo.getView(android.R.id.button3));
		// Sleep for 2567 milliseconds
		solo.sleep(2000);//2567);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1532 milliseconds
		solo.sleep(2000);//1532);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1390 milliseconds
		solo.sleep(2000);//1390);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1608 milliseconds
		solo.sleep(2000);//1608);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1174 milliseconds
		solo.sleep(2000);//1174);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1507 milliseconds
		solo.sleep(2000);//1507);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 6547 milliseconds
		solo.sleep(2000);//6547);
		// Click on ImageView
		solo.clickOnView(solo.getView(R.id.gender));
		// Sleep for 3064 milliseconds
		solo.sleep(2000);//3064);
		// Click on ImageView
		solo.clickOnView(solo.getView(R.id.gender));
		// Sleep for 4741 milliseconds
		solo.sleep(2000);//4741);
		// Assert that: '20' is shown
		assertTrue("'20' is not shown!", solo.waitForView(solo.getView(R.id.total_level)));
	
		
		solo.sendKey(solo.MENU);
		
		// Click on Reset 
		solo.clickInList(1, 0);
		// Sleep for 5455 milliseconds
		solo.sleep(2000);//5455);
		
		solo.sendKey(solo.MENU);
		solo.sleep(2000);//5455);
		// Click on Roll Dice 
		solo.clickInList(2, 0);
		// Wait for dialog
		solo.waitForDialogToOpen(20000);
		// Sleep for 4913 milliseconds
		solo.sleep(2000);//4913);
		// Click on Okay
		solo.clickOnView(solo.getView(android.R.id.button3));
		// Sleep for 4290 milliseconds
		solo.sleep(2000);//4290);
		
		solo.sendKey(solo.MENU);
		solo.sleep(2000);//5455);
		// Click on Roll Dice 
		solo.clickInList(2, 0);
		// Sleep for 3208 milliseconds
		solo.sleep(2000);//3208);
		// Click on Okay
		solo.clickOnView(solo.getView(android.R.id.button3));
		// Sleep for 3545 milliseconds
		solo.sleep(2000);//3545);
		
		solo.sendKey(solo.MENU);
		solo.sleep(2000);//5455);
		// Click on Roll Dice 
		solo.clickInList(2, 0);
		// Sleep for 2846 milliseconds
		solo.sleep(2000);//2846);
		// Click on Okay
		solo.clickOnView(solo.getView(android.R.id.button3));
		// Sleep for 4320 milliseconds
		solo.sleep(2000);//4320);
		
		solo.sendKey(solo.MENU);
		solo.sleep(2000);//5455);
		
		// Click on Change Settings 
		solo.clickInList(3, 0);
		// Wait for activity: 'info.bpace.munchlife.SettingsActivity'
		assertTrue("info.bpace.munchlife.SettingsActivity is not found!", solo.waitForActivity(SettingsActivity.class));
		// Sleep for 10262 milliseconds
		solo.sleep(2000);//10262);
		// Click on LinearLayout Keep Screen Awake Keep screen from sleeping LinearLayout
		solo.clickInList(2, 0);
		// Sleep for 5182 milliseconds
		solo.sleep(2000);//5182);
		// Click on LinearLayout Victory Dialog Select whether to display a dialog on reaching 
		solo.clickInList(3, 0);
		// Sleep for 3291 milliseconds
		solo.sleep(2000);//3291);
		// Click on LinearLayout Max Level The level at which you win. LinearLayout
		solo.clickInList(4, 0);
		// Wait for dialog
		solo.waitForDialogToOpen(20000);
		// Sleep for 4405 milliseconds
		solo.sleep(2000);//4405);
		// Click on 10
		solo.clickOnView(solo.getView(android.R.id.edit));
		// Sleep for 20007 milliseconds
		solo.sleep(2000);//20007);
		// Enter the text: '100000'
		solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
		solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "100000");
		// Sleep for 1882 milliseconds
		solo.sleep(2000);//1882);
		// Click on Cancel
		solo.clickOnView(solo.getView(android.R.id.button2));
		// Sleep for 2900 milliseconds
		solo.sleep(2000);//2900);
		// Click on LinearLayout Max Level The level at which you win. LinearLayout
		solo.clickInList(4, 0);
		// Enter the text: '10'
		solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
		solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "10");
		// Wait for dialog
		solo.waitForDialogToOpen(20000);
		// Sleep for 7167 milliseconds
		solo.sleep(2000);//7167);
		// Click on 10
		solo.clickOnView(solo.getView(android.R.id.edit));
		// Sleep for 4829 milliseconds
		solo.sleep(2000);//4829);
		// Enter the text: '5'
		solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
		solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "5");
		// Sleep for 1930 milliseconds
		solo.sleep(2000);//1930);
		// Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
		// Sleep for 4300 milliseconds
		solo.sleep(2000);//4300);
		// Press menu back key
		solo.goBack();
		// Sleep for 2331 milliseconds
		solo.sleep(2000);//2331);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1347 milliseconds
		solo.sleep(2000);//1347);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1927 milliseconds
		solo.sleep(2000);//1927);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1198 milliseconds
		solo.sleep(2000);//1198);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1501 milliseconds
		solo.sleep(2000);//1501);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 2375 milliseconds
		solo.sleep(2000);//2375);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 3901 milliseconds
		solo.sleep(2000);//3901);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 3310 milliseconds
		solo.sleep(2000);//3310);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 1114 milliseconds
		solo.sleep(2000);//1114);
		// Click on Remove Gear
		solo.clickOnView(solo.getView(R.id.down_gear_button));
		// Sleep for 1356 milliseconds
		solo.sleep(2000);//1356);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1123 milliseconds
		solo.sleep(2000);//1123);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1829 milliseconds
		solo.sleep(2000);//1829);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 2239 milliseconds
		solo.sleep(2000);//2239);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 1382 milliseconds
		solo.sleep(2000);//1382);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 6305 milliseconds
		solo.sleep(2000);//6305);
		// Click on ImageView
		solo.clickOnView(solo.getView(R.id.gender));
		// Sleep for 2398 milliseconds
		solo.sleep(2000);//2398);
		// Click on ImageView
		solo.clickOnView(solo.getView(R.id.gender));
		// Sleep for 2076 milliseconds
		solo.sleep(2000);//2076);
		// Click on ImageView
		solo.clickOnView(solo.getView(R.id.gender));
		// Sleep for 2082 milliseconds
		solo.sleep(2000);//2082);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1915 milliseconds
		solo.sleep(2000);//1915);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1311 milliseconds
		solo.sleep(2000);//1311);
		// Click on Add Gear
		solo.clickOnView(solo.getView(R.id.up_gear_button));
		// Sleep for 1190 milliseconds
		solo.sleep(2000);//1190);
		// Click on Up a Level
		solo.clickOnView(solo.getView(R.id.up_button));
		// Sleep for 1230 milliseconds
		solo.sleep(2000);//1230);
		// Click on Remove Gear
		solo.clickOnView(solo.getView(R.id.down_gear_button));
		// Sleep for 1155 milliseconds
		solo.sleep(2000);//1155);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 1128 milliseconds
		solo.sleep(2000);//1128);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 905 milliseconds
		solo.sleep(2000);//905);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 1413 milliseconds
		solo.sleep(2000);//1413);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 839 milliseconds
		solo.sleep(2000);//839);
		// Click on Down a Level
		solo.clickOnView(solo.getView(R.id.down_button));
		// Sleep for 869 milliseconds
		solo.sleep(2000);//869);
		// Click on Remove Gear
		solo.clickOnView(solo.getView(R.id.down_gear_button));
	}
}
