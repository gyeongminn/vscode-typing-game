# VSCode Typing Game

In the world of programming languages, common words are falling apart. Users can choose between C, Java, and Python. Words are disassembled into the syntax of the chosen language. Entering words correctly in the terminal earns you gold, while failing to do so results in gold deduction and a decrease in health. The overall design of the game is reminiscent of Visual Studio Code.

When the game starts, a health gauge is created. As time passes, health gradually decreases, and when it reaches zero, your earned gold is saved, and the game ends. Typing words of the same color consecutively builds a combo, allowing you to earn more points. The order in which users input words affects their score.

There are a total of 4 items in the game. Clicking on them when they drop allows you to use the items. Acquiring "Coffee" and "Energy Drink" items activates the boost mode for 10 seconds. During boost mode, health does not decrease, and you can earn more gold. Acquiring the "Nap" item restores health, and for 5 seconds, words fall slowly. Acquiring the "Google" item clears all currently generated words. However, you can only earn 50% of the total gold you would have obtained by typing those words.

On the left panel, clicking on the leftmost icons allows you to switch between panels. The Score panel displays your current gold, the number of words correctly typed in this game, and the number of items used. The Edit panel shows all currently loaded words and allows you to add new ones. In the Settings panel, you can select a level to adjust the word falling speed and change the language.

To start the game, type "start" in the terminal. Typing "pause" in the terminal will pause the game, and you can resume it by typing "start" again. Type "stop" to end the game. Typing "exit" in the terminal will forcibly close the program.

The top menu also allows you to select the language and level and control the start/pause/stop of the game.

![image](https://user-images.githubusercontent.com/97784561/209441559-a9bfe56c-f8ea-4a51-b23b-469dbebb4190.png)
