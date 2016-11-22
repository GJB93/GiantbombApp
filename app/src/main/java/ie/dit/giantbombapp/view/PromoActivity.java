package ie.dit.giantbombapp.view;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

public class PromoActivity extends AppCompatActivity {

    private static final String TAG = "PromoActivity";
    private MainController controller;
    private Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_activity);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //controller.fetchPromo(12229);
        controller.fetchAllPromos();

        //controller.fetchPromoG(12229);
        //controller.fetchAllPromosG();
    }
}
