package cardexc.com.practicework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 115;

    private OnAddFinish mActivity;

    private ImageView camera_image;
    private TextView selectedDistrict;
    private EditText editText_place;
    private static EditText editText_date;
    private static EditText editText_time;
    private Button button_set_date;
    private Button button_set_time;
    private Button button_set_district;

    private Bitmap image;

    public interface OnAddFinish{
        void OnAddFinish(Intent intent);
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = (OnAddFinish) activity;
        super.onAttach(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_layout, container, false);

        setHasOptionsMenu(true);

        camera_image = (ImageView) view.findViewById(R.id.camera_image);
        editText_place = (EditText) view.findViewById(R.id.addLayout_editText_place);
        editText_date = (EditText) view.findViewById(R.id.addLayout_editText_date);
        editText_time = (EditText) view.findViewById(R.id.addLayout_editText_time);
        selectedDistrict = (TextView) view.findViewById(R.id.addLayout_selectedDistrict);

        button_set_date = (Button) view.findViewById(R.id.addLayout_button_set_date);
        button_set_time = (Button) view.findViewById(R.id.addLayout_button_set_time);
        button_set_district = (Button) view.findViewById(R.id.addLayout_button_setDistrict);

        /*spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.array_districts, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);*/

        camera_image.setOnClickListener(onImageClick());
        button_set_date.setOnClickListener(onSetDateClick());
        button_set_time.setOnClickListener(onSetTimeClick());
        button_set_district.setOnClickListener(onSetDistrictClick());
        return view;

    }

    @NonNull
    private View.OnClickListener onSetDistrictClick() {


        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int[] clicked = new int[1];

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setSingleChoiceItems(R.array.array_districts, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clicked[0] = which;
                            }
                        })
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String[] districts = getResources().getStringArray(R.array.array_districts);
                                String district = districts[clicked[0]];

                                selectedDistrict.setText(district);

                                button_set_district.setVisibility(View.GONE);
                                selectedDistrict.setVisibility(View.VISIBLE);

                            }
                        })
                        .setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }


        };
    }

    @NonNull
    private View.OnClickListener onSetTimeClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "timePicker");
            }
        };
    }

    @NonNull
    private View.OnClickListener onSetDateClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        };
    }

    @NonNull
    private View.OnClickListener onImageClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.action_done): {

                Intent intent = new Intent();

                Place place = new Place();

                if (image != null)
                    place.setImage(image);

                if (!"".equals(editText_place.getText().toString()))
                    place.setPlace(editText_place.getText().toString());

                String datetime = "";

                if (!"".equals(editText_time.getText().toString()))
                    datetime = editText_time.getText().toString();

                if (!"".equals(editText_date.getText().toString()))
                    datetime += "; " + editText_date.getText().toString();

                place.setDateTime(datetime);
                place.setDistrict(selectedDistrict.getText().toString());

                intent.putExtra("place", place);
                mActivity.OnAddFinish(intent);

                return true;
            }
        }
        return true;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
    }

    public void takePicture() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            image = data.getParcelableExtra("data");
            camera_image.setImageBitmap(image);
        }


    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            editText_date.setText(year + "/" + month + "/" + day);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            editText_time.setText(hourOfDay + ":" + minute);
        }
    }

}
