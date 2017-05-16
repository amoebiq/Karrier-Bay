package activity;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yourapp.developer.karrierbay.R;
import com.yourapp.developer.karrierbay.dummy.DummyContent;

import Utilities.CircleTransform;

/**
 * A fragment representing a single Carrier detail screen.
 * This fragment is either contained in a {@link CarrierListActivity}
 * in two-pane mode (on tablets) or a {@link CarrierDetailActivity}
 * on handsets.
 */
public class CarrierDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String USER_NAME = "name";
    public static final String ADDRESS = "address";
    public static final String IMAGE = "image_url";
    public static final String FROM_ADDRESS = "from_address";
    public static final String TO_ADDRESS = "to_address";
    public static final String CATEGORY = "category";
    public static final String DATE_FROM = "from_date";
    public static final String ITEM_WEIGHT = "item_weight";
    public static final String DATE_TO = "to_date";
    public static final String STOP_OVERS = "stop_overs";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarrierDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.carrier_detail, container, false);

        // Show the dummy content as text in a TextView.

        String image_url = getArguments().getString(IMAGE);
        ImageView iv = (ImageView) rootView.findViewById(R.id.carrier_detail_image);
        Picasso.with(rootView.getContext())

                .load(image_url)
                .placeholder(R.drawable.carrier)
                .error(R.drawable.myimage).transform(new CircleTransform())
                .into(iv);


        ((TextView) rootView.findViewById(R.id.carrier_detail_name)).setText(getArguments().getString(USER_NAME));
        ((TextView) rootView.findViewById(R.id.carrier_detail_address)).setText(getArguments().getString(ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_from)).setText(getArguments().getString(FROM_ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_to)).setText(getArguments().getString(TO_ADDRESS));
        ((TextView) rootView.findViewById(R.id.carrier_detail_from_date)).setText(getArguments().getString(DATE_FROM));
        ((TextView) rootView.findViewById(R.id.carrier_detail_to_date)).setText(getArguments().getString(DATE_TO));
        ((TextView) rootView.findViewById(R.id.carrier_detail_category)).setText(getArguments().getString(CATEGORY));

        return rootView;
    }
}
