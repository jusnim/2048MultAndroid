//package com.example.a2048mult.ui.game;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//
//import com.example.a2048mult.databinding.FragmentGameBinding;
//import com.example.a2048mult.game.GameState;
//import com.example.a2048mult.game.logic.Player;
//import com.example.a2048mult.ui.game.playfield.PlayfieldUI;
//import com.example.a2048mult.ui.game.playfield.PlayfieldView;
//
//public class GameFragment2 extends Fragment implements GameUI {
//
//    private FragmentGameBinding binding;
//    private PlayfieldUI[] playfieldUIs;
//    private Player[] players;
//
//    public GameFragment2(@NonNull Context context) {
//        super(context);
//        init(context);
//    }
//
//    public GameFragment2(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public GameFragment2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    public GameFragment2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }
//
//    private void init(Context context) {
//        binding = FragmentGameBinding.inflate(LayoutInflater.from(context), this, true);
//
//
//    }
//
//    @Override
//    public void initGameState(GameState gameState) throws IllegalAccessException {
//        this.players = gameState.getAllPlayer();
//        for (int i = 0; i < this.players.length; i++) {
//            playfieldUIs[i] = new PlayfieldView(getContext());
//            playfieldUIs[i].drawPlayer(players[i]);
//        }
//
//        switch (this.players.length) {
//            case 1:
//                drawSingleplayer();
//                break;
//            case 2:
//                drawMultiplayer2();
//                break;
//            case 3:
//                drawMultiplayer3();
//                break;
//            case 4:
//                drawMultiplayer4();
//                break;
//            default:
//                throw new IllegalAccessException("Invalid amount of players. It should be >0");
//        }
//    }
//
//    private void drawSingleplayer(){
//        binding.getRoot().addView((View) playfieldUIs[0]);
//    }
//    private void drawMultiplayer2(){}
//    private void drawMultiplayer3(){}
//    private void drawMultiplayer4(){}
//    @Override
//    public void drawGameState(GameState gameState) {
//
//    }
//}
//
