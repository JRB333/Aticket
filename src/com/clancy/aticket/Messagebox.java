package com.clancy.aticket;

import android.content.Context;
import android.widget.Toast;

public class Messagebox {
	  public static void Message(String DisplayMessage, Context dan) {
	   Toast.makeText(dan, DisplayMessage, Toast.LENGTH_LONG).show();
 }

}
