package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*Here we're going to create the fragment for each level.
 each level will contains:
   1. TextView - for the title of the level (level 1, level 2, exc...) based on an array
   2. GridLayout - for the display of levels. each cell will contain a levels_cell
      each cell will contain:
          a. The button that will link to the level
          b. The stars according to the array of Level object:
                  * 0 - The level not yet passed
                  * 1 - The level passed with a score between 0-99
                  * 2 - The level passed with a score between 100-199
                  * 3 - The level passed with a score 200 or above it
   3. An array of 16 levels (based on the Level object we created).
      we'll put each level cell inside the right place in the GridLayout.
   4. Button of "Play" - will link directly to the game activity of the top of the copied list of level
      (same as note #5)

  Notes:
  1. All the levels will start in status "not complete"
  2. All the "not complete" levels will be block to press
  3. The first level in the list (the top of the list of levels) won't be blocked but will be with 0 stars
  4. We will create a copy of the list of level for dynamic changes in game
  5. We will create an instance for the top of the copied list of levels's game activity
     (instance for result of score)
  6. When level is complete (returns from the game activity instance):
        * the number of stars changes according to score we received
        * the status of the level will change to "complete"
        * afterwords we pop the level from the copied list of levels
        * then change the top level on the copied list of levels so it will be able to pressed on
  7. We keep the option to press a level after finished it

  */

public class LevelsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.levels_fragment,container,false);

        TextView levelNumTv = root.findViewById(R.id.level_number);
        Button[][] levelsBtn = new Button[4][4];


        /*for each level create 4x4 buttons that will linked to game_activity*/


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
