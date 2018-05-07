
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.dina.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameText.getText().toString();
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasWhippedCream = whippedCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
      int price = calculatePrice(hasWhippedCream, hasChocolate);
       String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numOfCoffees);

    }


    /**
     * This method is called when the plus button is clicked
     */

    public void increment(View view) {
        if (quantity >= 100) {

            Toast.makeText(this, "You cannot have more than 100 Coffees" , Toast.LENGTH_SHORT).show();
                             return;

        } else {

            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the minus button is clicked
     */
    public void decrement(View view) {
        if(quantity<2){

            Toast.makeText(this,"You cannot have less than 1 Coffee" , Toast.LENGTH_SHORT).show();

            return;


    }
    else{
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }





    /**
     * Calculates the price of the order.
     *
     * @return the total price
     */
    private int calculatePrice(boolean extraWhipped, boolean extraChocolate) {

        int basePrice = 5;

        if (extraWhipped) {
            basePrice = basePrice + 1;

        } else if (extraChocolate) {

            basePrice = basePrice + 2;
        }


        return quantity * basePrice;


    }


    /**
     * Create Order Summary
     *
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String myName, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.customer_name,myName) ;
        priceMessage+= "\n"+getString(R.string.whippedCream)+ addWhippedCream + "\nAdd chocolate ?" + addChocolate + "\nQuantity:" + quantity + "\nTotal:" + price + "EGP \n"+getString(R.string.thank_you);
        return priceMessage;

    }


}