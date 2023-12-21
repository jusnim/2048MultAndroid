//package com.example.a2048mult.ui.menu;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.a2048mult.ui.game.playfield.PlayfieldUI;
//import com.example.a2048mult.ui.game.playfield.PlayfieldViewImpl;
//import com.example.a2048mult.databinding.FragmentChoosingPreviewBinding;
//
//public class ChoosingPreviewFragment extends Fragment {
//    private FragmentChoosingPreviewBinding binding;
//
//    private static final String PLAYFIELD_WIDTH = "param1";
//    private static final String PLAYFIELD_HEIGHT = "param2";
//
//    // TODO: Rename and change types of parameters
//    private int playfieldWidth;
//    private int playfieldHeight;
//
//    private View playfield;
//
//    public ChoosingPreviewFragment() {
//        // Required empty public constructor
//    }
//
//    public static ChoosingPreviewFragment newInstance(int playfieldWidth, int playfieldHeight) {
//        ChoosingPreviewFragment fragment = new ChoosingPreviewFragment();
//        Bundle args = new Bundle();
//        args.putInt(PLAYFIELD_WIDTH, playfieldWidth);
//        args.putInt(PLAYFIELD_HEIGHT, playfieldHeight);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            playfieldWidth = getArguments().getInt(PLAYFIELD_WIDTH);
//            playfieldHeight = getArguments().getInt(PLAYFIELD_HEIGHT);
//        }
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = FragmentChoosingPreviewBinding.inflate(getLayoutInflater());
//        init();
//        return binding.getRoot();
//    }
//
//    private void init() {
//
//        ViewGroup.LayoutParams params = binding.playfieldView1.getLayoutParams();
//        binding.getRoot().removeView(binding.playfieldView1);
//
//        protoPlayfield(this.playfieldWidth, this.playfieldHeight);
//        this.playfield.setLayoutParams(params);
//        binding.getRoot().addView(this.playfield);
//        binding.previewText.setText(playfieldWidth + "x" + playfieldHeight);
//    }
//
//    private void protoPlayfield(int width, int height) {
//        this.playfield = new PlayfieldViewImpl(binding.getRoot().getContext());
//
//        int level = 0;
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                ((PlayfieldViewImpl) this.playfield).spawnTileAt(x, y, level);
//                level++;
//            }
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
//
