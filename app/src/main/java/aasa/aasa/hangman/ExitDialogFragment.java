package aasa.aasa.hangman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import static aasa.aasa.hangman.R.id.exit_close;

/**
 * Created by arpit on 13/12/15.
 */
public class ExitDialogFragment extends DialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exit_dialog, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        TextView yesText = (TextView) rootView.findViewById(R.id.yes_text);
        TextView noText = (TextView) rootView.findViewById(R.id.no_text);
        ImageView close = (ImageView) rootView.findViewById(exit_close);

        yesText.setOnClickListener(this);
        noText.setOnClickListener(this);
        close.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.exit_dialog_width),
                getResources().getDimensionPixelSize(R.dimen.exit_dialog_height));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yes_text:
                getActivity().moveTaskToBack(true);
                getActivity().finish();
                break;
            case R.id.no_text:
                getDialog().cancel();
                break;
            case R.id.exit_close:
                getDialog().cancel();
                break;
            default:
                break;
        }
    }
}
