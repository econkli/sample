/**
 * Created by Emily on 11/8/15.
 */

//import necessary libraries for this class
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Window extends JFrame {

    //-----------------------DATA MEMBERS-----------------------\\

    //window settings
    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private JTextArea studentInfo;

    //deals with gameplay
    private String curr;
    private int len;
    private int index;
    private String[] textList;
    private Student student;
    private String chapter;
    private String checkpoint;
    private SmallTalk myGame;

    //-----------------------CONSTRUCTOR-----------------------\\

    /**
     * Creates the frame.
     */
    public Window() {
        chapter = "";
        student = new Student();

        setForeground(Color.DARK_GRAY);
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 15, 0));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();

        textField = new JTextField();
        textField.setText("> ");
        Font font = new Font("Courier", Font.BOLD,14);
        textField.setFont(font);
        textField.setColumns(10);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);

        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                        .addComponent(textField, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
        );

        textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        textArea.setFont(font);
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        contentPane.setLayout(gl_contentPane);

        studentInfo = new JTextArea();

        // start our set up of key bindings

        // to get the correct InputMap
        int condition = JComponent.WHEN_FOCUSED;
        // get our maps for binding from the chatEnterArea JTextArea
        InputMap inputMap = textField.getInputMap(condition);
        ActionMap actionMap = textField.getActionMap();

        // the key stroke we want to capture
        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

        // tell input map that we are handling the enter key
        inputMap.put(enterStroke, enterStroke.toString());

        // tell action map just how we want to handle the enter key
        actionMap.put(enterStroke.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (index<len) {
                    printToArea(curr);
                    index++;
                    getNext();
                }
                if (index==len) {
                    printToArea(curr);
                    textArea.append(">");
                    index++;
                }
                else if (index>len){
                    inputHandler();
                }
            }
        });
    }

    //-----------------------METHODS (WINDOW INPUT/OUTPUT)-----------------------\\

    public void printToArea(final String lineIn){
        textField.setText("");
        textArea.append(lineIn + "\n");
        textField.setText("> ");
        textField.requestFocus();
    }

    public void getTextIn(String in){
        textList = in.split("\\t");
        len = textList.length;
        if (len>1) {
            index = 1;
            printToArea(textList[0] + "\n");
            getNext();
        }
        else {
            index = 1;
            printToArea(textList[0] + "\n");
            textArea.append(">");
            index++;
        }
    }

    public void getNext(){
        if (index<len){
            curr = textList[index];
        }
        else {
            curr = "";
        }
    }

    public String userInput(){
        String userIn = textField.getText();
        if (!userIn.equals("> "))
            textArea.append(userIn + "\n" + "\n");
        else
            textArea.append("\n");
        textField.setText("> ");
        textField.requestFocus();
        return userIn;
    }

    public void clear(){
        textArea.setText("");
    }

    //-----------------------METHODS (STORY)-----------------------\\

    //-----------------------SAVE, HELP, QUIT-----------------------\\

    public void save(){
        checkpoint = chapter;
    }

    public void goToCheckpoint(){
        chapter = checkpoint;
    }

    public void help(){}

    public void quit(){
        textArea.setText("Are you sure you want to quit? Your progress will not be saved.");
        String answer = userInput();
        if(answer.toLowerCase().contains("y"))
            System.exit(0);
    }

    //-----------------------GENERIC INPUT CHECKERS-----------------------\\

    public boolean stringOkLength(int lower, int upper, String toCheck){
        if (toCheck.length()<upper && toCheck.length()>lower)
            return true;
        else if (toCheck.length()>upper){
            textArea.append("Sorry, too long. Try again!\n\n");
            return false;
        }
        else if (toCheck.length()<lower){
            textArea.append("Sorry, too short. Try again!\n\n");
            return false;
        }
        return false;
    }

    public boolean stringNotEmpty(String toCheck){
        if (toCheck.length()>0)
            return true;
        else{
            textArea.append("Gotta write something there, bud.\n\n");
            return false;
        }
    }

    public boolean stringOk(String[] array, String toCheck){
        String[] snarkyReplies = {"What are you trying to say?","Unacceptable...that's not...try again",
                "Try a better entry...that one's bad. Awkward...","Try again, motherfucker","Command not recognized. Try again."};

        //checks to see if input is in list of acceptable answers
        for (int i=0;i<array.length;i++){
            if (toCheck.toLowerCase().contains(array[i].toLowerCase())){
                return true;
            }
        }

        //if not acceptable, picks a random snarky comment
        Random rand = new Random();
        textArea.append(snarkyReplies[rand.nextInt(snarkyReplies.length)]+"\n\n");
        return false;
    }

    public boolean intWithinRange(int lower, int upper, int intCheck){
        if (intCheck<=upper && intCheck>=lower)
            return true;
        else if (intCheck>upper){
            textArea.append("Sorry, too big. Try again!\n\n");
            return false;
        }
        else if (intCheck<lower){
            textArea.append("Sorry, too small. Try again!\n\n");
            return false;
        }
        return false;
    }

    public boolean objectActionMap(HashMap<String, String[]> map, String toCheck) {
        String[] snarkyReplies = {"What are you trying to do?", "Command not recognized. Try again.", "You know that makes no sense.",
                "Computer stupid, no understand."};
        String[] check = toCheck.split(" ");

        for (int i = 0; i < check.length; i++) {
            if (map.containsKey(check[i])) {
                String[] myList = map.get(check[i]);
                for (int j = 0; j< myList.length; j++){
                    for (int k = 0; k<check.length; k++){
                        if (myList[j].equals(check[k]))
                            return true;
                    }
                }
            }
        }

        //if not acceptable, picks a random snarky comment
        Random rand = new Random();
        textArea.append(snarkyReplies[rand.nextInt(snarkyReplies.length)] + "\n\n");
        return false;
    }

    //-----------------------INTRO TEXT-----------------------\\
    public void intro1() {
        chapter = "intro1";
        String intro1 = "It's 2 PM, and you're late for your appointment with the high school counselor. " +
                "Your teacher kept refusing to let you leave her very important class (Crafts B), but " +
                "that's okay - you're here now. You knock on the counselor's door, and are welcomed into " +
                "an unfamiliar room. ”Hello there!” says the counselor brightly, as you take a seat. " +
                "”I don't think we've met yet!”\t" +
                "She's probably right. Your high school has a peculiarly high turnover rate for counselors." +
                " And it's not like you make much of an effort to see them anyway. \"Let's get started on " +
                "some basics here\", she continues. ”Just to make sure my records are all in order. Remind" +
                " me of your name?”\t";
        getTextIn(intro1);
    }

    public void intro2() {
        chapter = "intro2";
        String intro2 = "\"" + student.getName() + ", well, that's just lovely! And your gender identity?” (enter M for male, " +
                "F for female, or O for other)\t";
        getTextIn(intro2);
    }

    public void intro3() {
        chapter = "intro3";
        String intro3 = "”Super. Now, I don't want to pry, but where does your family fall on a scale of " +
                "dirt poor to filthy rich?”\n" +
                "   [1] - Extremely poor\n" +
                "   [2] - We get by\n" +
                "   [3] - Just average, I guess\n" +
                "   [4] - \"Well-off\"\n" +
                "   [5] - My parents collect yachts for fun\t";
        getTextIn(intro3);
    }

    public void intro4() {
        chapter = "intro4";
        String intro4 = "”Okey dokey. Now, on a scale of 0-100, what are your grades like? As a school " +
                "counselor this is something I should know, but I'm extremely incompetent, so you'll have " +
                "to help me out.”\t";
        getTextIn(intro4);
    }

    public void intro5a() {
        chapter = "intro5";
        String intro5a = "”Yikes. Well, you somehow managed to get into five colleges anyway. Let’s see…you’ve been " +
                "accepted into:\n" +
                "   Ritzpaulroy Filibuster University\n" +
                "   Iliad College\n" +
                "   Freedom University\n" +
                "   SUNY Corple\n" +
                "   TC2\nAs you know, the deadline’s coming up. Actually, it’s today. " +
                "Have you made a choice yet, or would you like me to tell you a little more about any of " +
                "these schools?” (for more information, type 'info' and the name of the selected college)\t";
        getTextIn(intro5a);
    }

    public void intro5b() {
        chapter = "intro5";
        String intro5b = "Mmhmm. Well, you somehow managed to get into five colleges anyway. Let’s see…" +
                "you’ve been accepted into:\n" +
                "   Ritzpaulroy Filibuster University\n" +
                "   Iliad College\n" +
                "   Freedom University\n" +
                "   SUNY Corple\n" +
                "   TC2\nAs you know, the deadline’s coming up. Actually, " +
                "it’s today. Have you made a choice yet, or would you like me to tell you a little more " +
                "about any of these schools?” (for more information, type 'info' and the name of the selected college)\t";
        getTextIn(intro5b);
    }

    public void intro5c() {
        chapter = "intro5";
        String intro5c = "”Very good! Because of your stellar grades, you’ve been able to get into five " +
                "colleges. Let’s see…you’ve been accepted into:\n   Ritzpaulroy Filibuster University\n   Iliad " +
                "College\n   Freedom University\n   SUNY Corple\n   TC2\nAs you know, the deadline’s coming up." +
                " Actually, it’s today. Have you made a choice yet, or would you like me to tell you a " +
                "little more about any of these schools?” (for more information, type 'info' and the name of " +
                "the selected college)\t";
        getTextIn(intro5c);
    }

    public void intro6() {
        chapter = "intro6";
        String intro6 = "Congratulations! All you have to do is send in this acceptance letter. Enjoy " +
                "your time at " + student.getCollege().getName() +"! We'll miss you here at Generic High, " +
                "uh...what was your name again?\t" +
                "LATER...\t";
        getTextIn(intro6);
        save();
    }

    //-----------------------SUMMER TEXT-----------------------\\

    public void summer1() {
        chapter = "summer1";
        String summer1 = "Ah, the summer after high school. You've spent most of it at your parents' "+student.getHome()+", " +
                "spending some time with friends and family before you go away to college forever. Orientation is only a " +
                "week away, but you're trying not to get too worked up about that yet. \tToday's a Saturday, and you " +
                "don't have many obligations besides sitting around your living room. What do you want to do?";
        getTextIn(summer1);
    }

    //lower wealth option
    public void summer2a(){
        chapter = "summer2";
        String summer2a = "A pause. \"Honey,\" begins your mom. \"Darling. Dear. This whole college thing seems awfully expensive." +
                " I'm sorry your father and I can't pay for you. Are you sure you can afford this?\"";
        getTextIn(summer2a);
    }

    //middling wealth option
    public void summer2b(){
        chapter = "summer2";
        String summer2b = "A pause. \"Your father and I have gone over the expenses, and we'll be able to pay about half of your tuition. " +
                "Are you sure you can afford the rest?\"";
        getTextIn(summer2b);
    }

    //richy rich option
    public void summer2c(){
        chapter = "summer2";
        String summer2c = "A pause. \"Your father and I will be covering your tuition, of course, darling. We'll have to cut out one of our" +
                " yearly trips - the summer week in Bermuda, perhaps - but we all have to make sacrifices. Does that sound all right?\"";
        getTextIn(summer2c);
    }

    public void summer3(){
        chapter = "summer3";
        String summer3 = "She shrugs. \"Ok. One last question. You absolutely sure you want to go to college, "+student.getName()+"?\"";
        getTextIn(summer3);
    }

    public void summer4(){
        chapter = "summer4";
        String summer4 = "\"Well. I guess it doesn't matter anyway. How are you supposed to get a job without a degree?\"\t" +
                "Her words leave you a little uneasy, but she must be right. You can't decide if you're nervous or excited - " +
                "if only there was something you could do to make orientation come sooner! (There is. Hit enter.)\t";
        getTextIn(summer4);
        save();
    }

    //-----------------------ORIENTATION TEXT-----------------------\\

    public void orientation1(){
        chapter = "orientation1";
        String orientation1 = "8 AM, late August. Your mom's minivan pulls up to the curb outside the school - " +
                "a steady herd of parents and youths is streaming up the stairs in front of you. \"Want us to " +
                "walk you in?\" your mom asks.\t\"Nah,\" you reply. \"I'm a big "+student.getPronoun(5)+". I got this.\"" +
                " You take a deep breath and open the door before your bravado has a chance to fade. You jauntily " +
                "wave goodbye to your parents, heft your duffel bag over your shoulder, and join the herd of students. " +
                "Once you're up the stairs, you pause for a moment, taking in your new home. \n\t";

        if (student.getCollege().getIden()==0)
            orientation1 += "Ritzpaulroy University. You can almost smell the money in the air - your fellow students " +
                    "surround you in a sea of peacoats, handbags, and expensive watches. You follow them out into a " +
                    "beautiful quad, where students (as well as alumni in denial) lounge around on the grass. This is" +
                    " it, you're sure of it. This is place for you to become a successful intellectual. You're " +
                    "already proud to be a golden dragon, honestly.\n\t";
        else if (student.getCollege().getIden()==1)
            orientation1 += "Iliad College. Your father had called Iliad a \"damn hippie town\", but most of the " +
                    "people you see look normal enough, if slightly homogeneous. You've heard great things about this" +
                    " place - finally, a chance to get away from your small town. This is a hotbed of liberal activity, " +
                    "a place where you can...\n\t\"Fuck Corple!\" someone shouts behind you, interrupting your train of" +
                    " thought. You laugh, pretending you know what's going on.\n\t";
        else if (student.getCollege().getIden()==2)
            orientation1 += "Freedom University. You can tell immediately that these are your kind of people: god-" +
                    "fearing and extremely polite. Men and women maintain a respectful distance from each other " +
                    "(the school-mandated five feet) as you make your way to the quad. It's immaculate - the " +
                    "buildings are old and graceful, and you can see several chapels from where you're standing. " +
                    "You swear you can faintly hear a hymn being sung, but you're not sure where it's coming from.\n\t";
        else if (student.getCollege().getIden()==3)
            orientation1 += "SUNY Corple. You make your way past brick buildings and confused parents, making your" +
                    " way to an expansive quad. You're a little nervous, but hopefully this place will start feeling " +
                    "like home. \"Fuck Iliad!\" someone shouts behind you, and you laugh, pretending you know " +
                    "what's going on.\n\t";
        else if (student.getCollege().getIden()==4)
            orientation1 += "Tomply Community College. Maybe not the most prestigious of schools - you can still see" +
                    " the sneers on some of your classmate's faces when you told them where you were going. But it seems like a " +
                    "nice enough place, with well-groomed lawns and modern, if bland, architecture. Besides, with a $5000" +
                    " tuition, joke's on your classmates. TC2 seems like it has everything you need.\n\t";

        orientation1+="After you sign in and stick a nametag on your shirt, you dither, feeling a little out of place. " +
                "What do you do now?\n\t";
        getTextIn(orientation1);
    }

    public void orientation1a(){
        chapter = "orientation1a";
        String club = "";
        if (student.getCollege().getIden()==0) {
            club = "Young 1%-er's Club? It's something of a support group, so that students with very wealthy parents " +
                    "can feel that they're not alone. This year's discussion theme is the American Dream and how hard " +
                    "our parents worked to get here. Would you like to be put on our email list?\"\n";
        }
        else if (student.getCollege().getIden()==1) {
            club = "Students for Hypothetical Charity Work club? We meet every other week to discuss all the organizations we'd" +
                    " like to support if we had the time, energy, and money. Would you like to be put on our email list?\"\n";
        }
        else if (student.getCollege().getIden()==2) {
            club = "Young Conservatives Club? It's actually mandatory if you attend this school. I'll put your name down on our" +
                    " email list. That all right with you?\"\n";
        }
        else if (student.getCollege().getIden()==3) {
            club = "";
        }
        else if (student.getCollege().getIden()==4) {
            club = "Frisbee Golf Club? It's a big thing here. Don't ask me why. Would you like to be put on our email list?\"\n";
        }
        getTextIn(club);
    }

    public void orientation1b(){
        chapter = "orientation1b";
        String orientation1b = "***TRY TO MAKE FRIENDS?***\n";
        getTextIn(orientation1b);
    }

    public void orientation1c(){
        chapter = "orientation1c";
        String orientation1c = "To make friends, you have to appear interesting, but you can't be too out there. When the window " +
                "opens, you will see three fine young students, with a blue score (BORING) and a red score (WEIRD) underneath. These " +
                "scores are the students' perceptions of you. To win them over, you must keep your weird and boring levels balanced.\n\n" +
                " To play, first click the die, from which you will get either a blue (BORING) or red (WEIRD) number. Click a " +
                "student's picture to add this number to one of their scores. At the end of 60 seconds, make sure each student's boring/weird " +
                " meters aren't more than five away from each other!\tReady?\t***INITIATING SMALL TALK***\t";
        getTextIn(orientation1c);
    }

    public void orientation2(){
        chapter = "orientation2";
        String orientation2 = "The tour guide leads you on a merry tour of the campus. You've taken it before - there's nothing particularly" +
                " notable, except for maybe the ";
        if (student.getCollege().getIden()==0) {
            orientation2+="massive castle that looks exactly like Hogwarts. Complete with owls. You can see why people pay so much" +
                    " money to go here.\t";
        }
        else if (student.getCollege().getIden()==1) {
            orientation2+="multiple shrines to Rod Serling. There are students appearing to pray around some of them, and you ask " +
                    "the tour guide about it. He looks shocked at the question. \"How can you be so religiously intolerant?\" he asks." +
                    " You let it go.\t";
        }
        else if (student.getCollege().getIden()==2) {
            orientation2+="exceptionally bland, unseasoned food you eat at your brief stop in the dining hall. Freedom University is " +
                    "about 90% white, though, so maybe that's to be expected.\t";
        }
        else if (student.getCollege().getIden()==3) {
            orientation2+="bus you get on halfway through the tour. It takes you through the city, then out along some back roads, then " +
                    "back into the city, finally reaching the other part of campus. Your tour guides happily notes the efficient public " +
                    "transportation here.\t";
        }
        else if (student.getCollege().getIden()==4) {
            orientation2+="absurd amount of people you recognize from your high school. Since you were looking forward to getting away from " +
                    "them, this is a disappointment.\t";
        }
        orientation2+="You also see a couple hollow-eyed upperclassman on your tour - they regard your group with something almost " +
                "like pity. As they pass by, one whispers something - it almost sounds like \"get out while you still can\". You " +
                "must be mishearing him, though. \n\t\"" +
                "If you'll follow me to the computer lab,\" says the tour guide as your tour draws to an end, \"it's time to " +
                "meet your advisors.\" Entering the room, you awkwardly stand against one of the computer lab walls until your name " +
                "is called. Your advisor is a stern-looking man with white hair - he beckons you over to sit at the computer next to him.\n\t" +
                "You tell him that you're not sure what you want to major in, and he sighs.\n\t\"Well, we've had a few budget cuts recently, " +
                "so we dropped some of the extra majors,\" he tells you. \"You know, the less important ones, like women's " +
                "studies. We two have two splendid major left to choose from, though: Humanities or Science. Which will it be?\"";
        getTextIn(orientation2);
    }

    public void orientation3(){
        chapter = "orientation3";
        String orientation3 = "Excellent choice! I'm a "+student.getMajor()+" professor, you see. I can vouch that we've got a really" +
                " great program here. Now, would you like to declare a minor as well?\t";
        getTextIn(orientation3);
    }

    public void orientation3a(){
        chapter = "orientation3a";
        String orientation3a = "Excellent! It can be anything, really, just enter it here:\t";
        getTextIn(orientation3a);
    }

    public void orientation3b(){
        chapter = "orientation3b";
        String orientation3b = "No worries. You can always declare one later (maybe).\t";
        getTextIn(orientation3b);
    }

    public void orientation4(){
        chapter = "orientation4";
        String orientation4 = "\"Well, "+student.getName()+", you're all set! "+student.getMajor()+" major";
        if (student.getMinor().length()>0)
            orientation4+=" with a "+student.getMinor()+" minor";
        orientation4+=". You've got great things in store for you, I'm sure! Well, there's just one more thing I like to have students do. " +
                "Here,\" he says, passing you a printout. \"Mark down all of these goals you'd like to accomplish by the end of your" +
                " college career...\"\tYou look down at the paper. The first line says: \n[ ] DISCOVER YOUR PASSIONS. Check the check " +
                "box?";
        getTextIn(orientation4);
    }

    public void orientation4a(){
        chapter = "orientation4a";
        String orientation4a = "[ ] MAKE LIFELONG FRIENDS";
        getTextIn(orientation4a);
    }

    public void orientation4b(){
        chapter = "orientation4b";
        String orientation4b = "[ ] GET INVOLVED ON CAMPUS";
        getTextIn(orientation4b);
    }

    public void orientation4c(){
        chapter = "orientation4c";
        String orientation4c = "[ ] GET INVITED TO COOL PARTIES";
        getTextIn(orientation4c);
    }

    public void orientation4d(){
        chapter = "orientation4d";
        String orientation4d = "[ ] LEARN \"REAL WORLD\" SKILLS";
        getTextIn(orientation4d);
    }

    public void orientation4e(){
        chapter = "orientation4e";
        String orientation4e = "[ ] SLAY THE MONSTROUS BEAST THAT HAS BEEN PICKING OFF STUDENTS";
        getTextIn(orientation4e);
    }

    public void orientation4f(){
        chapter = "orientation4f";
        String orientation4f = "[ ] GRADUATE ON TIME";
        getTextIn(orientation4f);
    }

    public void orientation5(){
        chapter = "orientation5";
        String orientation5 = "";
        if (student.getNumGoals()==0)
            orientation5 += "\"Well, you're very ambitious,\" your advisor grumbles as you hand him the paper. ";
        else
            orientation5 += "You hand him the paper with a sense of excitement. You can't wait to "+student.goalToString()+"!";
        orientation5 += " \"Run along now,\" the stern man says, sternly. \"Enjoy your orientation, and don't forget that your" +
                " summer reading is due in two weeks!\"\n\tLATER...\t";
        getTextIn(orientation5);
    }

    //-----------------------INTERMISSION TEXT-----------------------\\

    public void intermission1(){
        chapter = "intermission1";
        String intermission1 = "Hold up a sec, esteemed player! We're going to jump out of the game real quick because I want to " +
                "explain something. You know how you go to college for four years? Yeah? That's not happening here. You're " +
                "going to go to school for one semester, to avoid lengthy repetitiveness. We good? I want to go over it again, " +
                "so you don't get confused.\tHow many semesters are you going to college in this shitty game?\t";
        getTextIn(intermission1);
    }

    public void intermission2(){
        chapter = "intermission2";
        String intermission2 = "Right. And how many years do people attend college in real life?\t";
        getTextIn(intermission2);
    }

    public void intermission3(){
        chapter = "intermission3";
        String intermission3 = "Actually, wrong! A recent report (Complete College America, 2013) found that only 39% " +
                "of students complete graduation requirements within four years - the average student takes six.\tThis " +
                "has been a brief educational interlude - please, carry on.\t";
        getTextIn(intermission3);
    }

    //-----------------------FIRST SEMESTER TEXT-----------------------\\

    public void firstSem1(){
        chapter = "firstSem1";
        String firstSem1 = "It's one of the strangest feelings you've ever experienced. All of your things are unpacked, kind of - " +
                "they're out of their boxes, at least. Your family hovers around for a few minutes, until you tell them you can" +
                " finish up on your own. They hug you goodbye - your mom's stoic, your dad's tearful - and then you are left alone " +
                "in your room.\tWhich is a lot smaller than it looked in pictures. There's not quite enough room for all the " +
                "furniture, and the stained carpet is emitting some faint but odd smell. It's all good, though. You're going to " +
                "find your academic genius in this room, and nothing can bring you down. \t\n";
        student.setRoommate();
        firstSem1+="There's a knock at the door, and you turn to see your new roommate, "+student.getRmName()+". You introduce" +
                    " yourself nervously - oh god, are you making an okay first impression? (to learn more about your roommate, type 'info')";
        getTextIn(firstSem1);
    }

    public void firstSem2(){
        chapter = "firstSem2";
        String firstSem2 = "You make slightly awkward conversation with "+student.getRmName()+" for a few minutes (you " +
                "are both trying very hard, though!) until your roommate suggests going down to the dining hall, because it is " +
                "lunchtime, after all. Do you want to go with them?";
        getTextIn(firstSem2);
    }

    public void firstSem2a(){
        chapter = "firstSem2a";
        String firstSem2a = "You readily agree, and the two of you walk across the quad to the dining hall. Smiling " +
                "freshmen are milling about everywhere, and you have to wait in line a while to get in. Once inside, you" +
                " spy some soggy pizza and vaguely \"Mexican\" food. Which will you have?";
        getTextIn(firstSem2a);
    }

    public void firstSem2a2(){
        chapter = "firstSem2a2";
        String firstSem2a2 = "It's pretty gross, and you say so to "+student.getRmName()+", hoping to bond over the " +
                "disappointing food. \"Yeah,\" they reply. \"I heard it was better before Dosexo took over.\"\t\"Dosexo?\" " +
                "you ask. \"That sounds like a made-up evil corporation.\"\n\tTo the table next to you is a member of the dining " +
                "hall staff, who seems to be listening in on your conversation. Talk to them?";
        getTextIn(firstSem2a2);
    }

    public void firstSem2a3(){
        chapter = "firstSem2a3";
        String firstSem2a3 = "\"You know,\" he says, looking thoughtful. \"I'm actually looking to hire a couple people to " +
                "man the slop booth. Would either of you be interested?\" (this will help decrease your debt, if you have any, but" +
                " it will increase your stress).";
        getTextIn(firstSem2a3);
    }

    //yes to job
    public void firstSem2a4(){
        chapter = "firstSem2a4";
        String firstSem2a4 = "Oh, awesome! Know how many hours you want to work?\n" +
                "   5 hours/week: $480/semester, +1 stress\n" +
                "   10 hours/week: $960/semester, +2 stress, +1 sleep deprivation\n" +
                "   15 hours/week: $1440/semester, +2 stress, +2 sleep deprivation\n" +
                "   20 hours/week: $1920/semester, +3 stress, +3 sleep deprivation\n" +
                "Or, if you've changed your mind, just say so.\n";
        getTextIn(firstSem2a4);
    }

    //no to job - walking back with roommate
    public void firstSem2a5(){
    chapter = "firstSem2a5";
    String firstSem2a5 = "What about your friend here?\" he asks, nodding to "+student.getRmName()+". \"Oh, I'm good,\"" +
            student.getRmName()+"says quickly. I already have a job, through the underground Communist...uh, through" +
            " the school paper.\" You chat with the man a little longer before he excuses himself to go back to the " +
            "kitchen.\tYou and "+student.getRmName()+" finish up your dinner and head back. About halfway there, your roommate " +
            "stops and peers at you intensely. \"Well?\" they say.";
        getTextIn(firstSem2a5);
    }

    //walking back with roommate
    public void firstSem2a6(){
        chapter = "firstSem2a6";
        String firstSem2a6 = "\"Well? Are we friends, like that guy said?\"";
        getTextIn(firstSem2a6);
    }

    //not going to dinner with your roommate
    public void firstSem2b(){
        chapter = "firstSem2b";
        String firstSem2b = "You decide that you'd better stay here and unpack, so your roommate goes ahead without you. In" +
                " no time at all, you've got everything arranged like you want it - the strand of Christmas lights, the band " +
                "posters, the pictures of you and your high school friends being cool. You decide you'd better go grab some food, " +
                "so you walk outside into the sunlight. Look around?\t";
        getTextIn(firstSem2b);
    }

    //first encounter with Crazy Joe
    //plot meetup between going to dinner/not going to dinner
    public void firstSem3() {
        chapter = "firstSem3";
        String firstSem3 =  "The calm afternoon is suddenly broken up by a shout. A little way in front of you, a man runs out onto " +
                "the path - he has a long beard and is festooned with campaign buttons. \"Find your dream!\" he shouts, " +
                "maybe to the sky, maybe to a tree. \"More like find your place in the corporate machinery, hahahaha!\"\t" +
                "You stand stock-still for a moment, bewildered, until someone taps on your shoulder. It's your roommate. \"Oh," +
                " that's just Crazy Joe, don't worry. I met him during orientation. He says weird shit, but he's totally " +
                "harmless.\"\n\t"+student.getRmName()+" puts a hand on your shoulder and gently steers you past the raving man. " +
                "As you pass, Crazy Joe's eyes focus on you, and he whispers in a voice only you can hear: \"Beware. Beware the beast.\"" +
                "\n\tThat's weird as fuck, right?\n\tLATER THAT NIGHT...";
        getTextIn(firstSem3);
    }

    //sitting in your room
    public void firstSem4() {
        chapter = "firstSem4";
        String firstSem4 = "You're sitting at your computer - you can't sleep, and your only companions are "+student.getRmName()+"'s " +
                "snoring and Dave Grohl, whose kind face looks down at you from your poster. Your head is buzzing with everything you've " +
                "seen today.\tA gentle 'ding' sounds from your computer, and you click your email tab. It's a campus-wide message " +
                "from the president, all caps, and it reads as follows:\n\t" +
                "DEAR STUDENTS,\n" +
                "AS YOU MAY HAVE HEARD, THERE HAVE BEEN SEVERAL CONFIRMED INTERACTIONS WITH THE CAMPUS BEAST. THESE INTERACTIONS" +
                " RANGE FROM SIGHTINGS (17) TO KILLINGS (6). BE THIS AS IT MAY, REST ASSURED THAT WE ARE TAKING EVERY NECESSARY " +
                "PRECAUTION TO ENSURE STUDENT WELL-BEING. WE ARE ALL DEDICATED TO MAKING "+(student.getCollege().getName()).toUpperCase()+
                " A SAFE, INCLUSIVE ENVIRONMENT FOR ALL. TO CONTRIBUTE, I ASK THAT YOU DO YOUR PART, AND REPORT ANY SIGHTINGS, MAIMINGS," +
                " OR KILLINGS TO PUBLIC SAFETY WITHIN A REASONABLE AMOUNT OF TIME. ENJOY YOUR STUDIES!\nTOODOLOO,\nPRESIDENT DOUG";
        getTextIn(firstSem4);
    }

    public void firstSem5() {
        chapter = "firstSem5";
        String firstSem5 = "What do you do now?";
        getTextIn(firstSem5);
    }

    public void firstSem6() {
        chapter = "firstSem6";
        String firstSem6 = "As it turns out, you have different things to worry about in the morning. You wake to your roommate shaking you " +
                "urgently by the shoulder. \"C'mon, we're gonna be late!\"\tYou sleepily pull yourself out of bed - what went wrong? you set " +
                "multiple alarms - and gather your stuff together, following "+student.getRmName()+" out the door. You have three classes today: "+
                student.getCollege().classesToString()+". \"I've got 'Understanding 'Diversity' first,\" your roommate exclaims cheerfully. \"I " +
                "guess it's all about how much richer your life is if you're around people from different backgrounds.\" That certainly sounds " +
                "more interesting than "+student.getCollege().getClasses()[1]+". Maybe you can switch.\n\t";

        firstSem6 += "As you walk, you see Crazy Joe wandering around again. \"Graduate school is a pyramid scheme!\" he yells at the clouds." +
                "\"And proper grammar are a tool of the oppressor!\" He then turns, as if he's looking into an invisible camera. \"Just remember that, " +
                "should you notice any spelling or grammar mistakes in this game.\" What a weird guy.\n\tYou make it to your first class before you realize " +
                "that you never asked "+student.getRmName()+" about the email...\tLATER...";
        getTextIn(firstSem6);
    }

    public void firstSem7() {
        chapter = "firstSem7";
        String firstSem7 = "\"Please have the textbook purchased before our next class!\" Professor Plum calls out at the end of class, as you and your" +
                " fellow students are packing your bags. You've looked the book up on the bookstore website - it costs $197, and is written by none " +
                "other than Professor Plum. She seems like she knows what she's doing, though - maybe could you ask her about the email. Do you want to " +
                "stick around and talk to her?";
        getTextIn(firstSem7);
    }

    public void firstSem7a() {
        chapter = "firstSem7a";
        String firstSem7a = " The professor looks thoughtful. \"Oh yes, it's very real, although I've never personally seen it. " +
                "It seems to go after adjunct professors, mostly. And poorer students. I'm tenured, so I don't I have much to worry about. Just be " +
                "careful walking around campus at night, okay?\"\n\tYou thank her and walk away, somehow feeling more confused than you had before. " +
                "You'll get to the bottom of this. Maybe, I mean. If you have time and everything.\n\tLATER...";
        getTextIn(firstSem7a);
    }

    public void firstSem8() {
        chapter = "firstSem8";
        String firstSem8 = "A few weeks in, 10 PM. You're working on your very first college paper, and you're going to do an awesome job. Right? " +
                "You have the header and title done, and your margins set up and everything. That deserves a break, right?";
        getTextIn(firstSem8);
    }

    public void firstSem8a() {
        chapter = "firstSem8a";
        String firstSem8a = "Okay. You're settled and ready to keep writing when you get a text. It's from a friend, asking if you want to go get " +
                "food with them. Do you?";
        getTextIn(firstSem8a);
    }

    public void firstSem8b() {
        chapter = "firstSem8b";
        String firstSem8b = "Okay. Okay. It's 1 AM. You're maybe halfway done with this goddamn paper (wait, this is Facebook. Go back to the paper). " +
                "Do you want to stay up and finish it, or do you just want to give in to sleep?";
        getTextIn(firstSem8b);
    }

    public void firstSem8c() {
        chapter = "firstSem8c";
        String firstSem8c = "You straggle to class the next morning to turn in what you have. Curiously, your normally sleepy classmates are abuzz." +
                "\"Someone else saw the beast last night!\" some girl tells you, excitedly. \"I don't really believe in it,\" says the boy sitting " +
                "next to her. \"I mean, I've never seen it. Like, supposedly it eats people, but it hasn't eaten me or anyone I know, so...I think " +
                "these people are just overreacting, honestly. What do you think? Do you believe in it?\"";
        getTextIn(firstSem8c);
    }

    public void firstSem8d() {
        chapter = "firstSem8d";
        String firstSem8d = "The rest of the class passes without incident, although you have trouble listening as the TA drones on and on. They're " +
                "talking about something called \"research opportunities\", whatever those are. You'd applied for a research position at the beginning of " +
                "the semester, but you never heard anything back, and the professor denied its existence when you asked. When the class is over, you head back to " +
                "your room.\tCrazy Joe is at it again - today he's got some kind of crown made of crumpled up paper. \"Hi, Crazy Joe!\" you call out, and as always," +
                " he stares right through you. \"Remember, higher education keeps the ruling class in power! Meritocracy is a myth!\" You nod politely and carry on.\n\t" +
                student.getRmName()+" is there when you get back, sitting at their computer. Except for the large shotgun leaning on the wall next to them, nothing" +
                " seems amiss.\n\t\"Hey, "+student.getName()+"\"! they say. \"Did you know that in Holland, a squirrel is called an Eichkatzerl, which translates to" +
                " 'little oak cat'? Isn't that cute?\"\n\tAddress the elephant in the room?";
        getTextIn(firstSem8d);
    }

    public void firstSem8e() {
        chapter = "firstSem8e";
        String firstSem8e = "\"The beast? Oh no, this is for protection from gunmen. They just passed a law that says we're allowed to have guns here. " +
                "You know, to defend ourselves and everything.\" You blink, but don't say anything - you're too tired to worry about it. Time for a nap? Yes? " +
                "Yes.";
        getTextIn(firstSem8e);
    }

    public void firstSem9(){
        chapter = "firstSem9";
        student.upStress();
        student.upSleepDepriv();
        String firstSem9 = "The rest of the semester passes without too much incident, and you find yourself falling into a consistent routine. The work " +
                "is almost constant - you sleep when you can, grab food with your friends sometimes. You haven't been invited to any parties, much to your" +
                " disappointment, but you tell yourself it's for the best. You still here mentions of the beast here and there - there are a few sightings a" +
                " month, and usually a death or two. It's never anyone you know, though, and you keep getting emails from the president reassuring you that " +
                "all is under control. You haven't told your parents about the issue, since you know they'd worry.\nLATER, MID-FINALS WEEK...";
        getTextIn(firstSem9);
    }

    public void firstSem10(){
        chapter = "firstSem10";
        student.upStress();
        student.upSleepDepriv();
        String firstSem10 = "You thought your work the rest of the semester was bad - now you've got three projects, two labs, and two papers due in the next " +
                "four days, with three tests to study for at the same time. You've never been this stressed in your life - but it's good for you, probably. " +
                "Adrenaline, or something. You want to cry but you've got things to do. " +
                "You've basically shut yourself in the library for the past 48 hours, and you're in the process of getting yet " +
                "another cup of coffee when you get a text from the roommate. I NEED YOUR HELP, it reads. IT'S TRYING TO GET IN. Go back to the room and see " +
                "what's up?";
        getTextIn(firstSem10);
    }

    //going to help roommate
    public void firstSem10a(){
        chapter = "firstSem10a";
        String firstSem10a = "There's a commotion outside your room - your door looks like it's been smashed down, and your RA is standing outside, looking " +
                "distressed. You can hear a shout from inside - your roommate - and sounds of a struggle. \"Get in there and help!\" you shout to the RA, " +
                "but he just shakes his head. \"Sorry, I can't. I'm anti-violence. You can't fight hate with hate, you know.\"\tPushing past him, you " +
                "run into your room to see your roommate grappling with a large beast. THE beast. It is real. What do you want to do?";
        getTextIn(firstSem10a);
    }

    //not going to help roommate
    public void firstSem10b(){
        chapter = "firstSem10b";
        String firstSem10b = "By the time you get back to your room around 4AM, something's very wrong. Your door appears to have been ripped off its" +
                " hinges, and your room is in complete disarray. Dave Grohl has been ripped to tatters, and your roommate's nowhere to be seen.\n\t\""+
                student.getName()+"!\" your RA shouts, running down the hall toward you. \"I've been trying to call you...the beast got "+student.getRmName()
                +". Just took him. I wanted to stop it, but...you know, I'm anti-violence. You just can't fight hate with hate. Sorry, man.\"";
        getTextIn(firstSem10b);
    }

    //failure to save roommate
    public void firstSem10c(){
        chapter = "firstSem10c";
        String firstSem10c = "Shocked, you wander around your very empty dorm room, staring at the broken glass. You spy a scrap of paper nearby - it's " +
                "a part of the Dave Grohl poster. How can he still be smiling at a time like this? \"Man, I'm sorry,\" your RA says again. \"That sucks. " +
                "I can help you clean up the glass and stuff. You want Insomnia Cookie or anything? On me. Well, partially. I only have like ten dollars.\" " +
                "You nod, only half-listening. \"Yeah.\" you say finally. \"Cookies would be good.\"\n\t LATER...\n";
        getTextIn(firstSem10c);
    }

    //success in saving roommate
    public void firstSem10d(){
        chapter = "firstSem10d";
        String firstSem10d = "Shocked, you and your roommate look at each other. \"That was...intense,\" your roommate says uncomfortably, and you agree. " +
                "\"Can I ask why you didn't go for the shotgun?\" you ask eventually, trying to find a way to bring down the adrenaline in the room. "+
                student.getRmName()+" shakes their head. \"I told you, man, that's for defense against gunmen.\" They look around, seemingly noticing the " +
                "level of destruction for the first time. \"This sucks. I can help you clean up the glass and stuff. You want Insomnia Cookie or anything? On me. " +
                "Well, partially. I only have like ten dollars.\" You nod, only half-listening. \"Yeah.\" you say finally. \"Cookies would be good.\"\n" +
                "\t LATER...\n";
        getTextIn(firstSem10d);
    }

    //aftermath either way
    public void firstSem11(){
        chapter = "firstSem11";
        String firstSem11 = "You finish up the semester in a sort of weird, dreamy state. Your professors are uncharacteristically nice to you, and you " +
                "get some A's you probably don't deserve. Professor Plum accepts your final paper late, only taking 33% off. It's right after you get out " +
                "of your last final that the email arrives, all in caps again:\n" +
                "DEAREST "+student.getName().toUpperCase()+",\n" +
                "WE AT THE ADMINISTRATIVE OFFICE REGRET TO HEAR ABOUT YOU AND YOUR ROOMMATE'S RUN-IN WITH THE ALLEGED \"BEAST\". WHILE THE SITUATION IS " +
                "LESS THAN IDEAL, IN GENERAL, WE CANNOT PREVENT ANY DEADLY BEAST-ON-STUDENT VIOLENCE. AS ALWAYS, WE TAKE STUDENT SAFETY VERY SERIOUSLY." +
                " IF YOU WOULD LIKE TO DISCUSS THE MATTER FURTHER, YOU ARE WELCOME TO STOP BY MY OFFICE AT 3 TODAY.\n" +
                "TA-TA,\nPRESIDENT DOUG\n\tWhat do you think? Are you going to this meeting?";
        getTextIn(firstSem11);
    }

    //meeting with president
    public void firstSem12(){
        chapter = "firstSem12";
        String firstSem12 = "A handsome secretary buzzes you in, and you walk into the president's office with some trepidation. The room's pretty " +
                "stereotypical - dark wood and brass, with a thick wooden desk. \"AH, HELLO!\" says President Doug, in the voice you would expect from " +
                "his exuberant emails. \"SO NICE TO MEET YOU, "+student.getName()+"! COME IN!\" You're already in, but you take a couple little steps " +
                "forward anyway.\n\t" +
                "\"I UNDERSTAND THERE'S BEEN SOME UNPLEASANTNESS,\" he says. \"THAT IS UNPLEASANT. IS THERE ANYTHING I CAN DO TO HELP?\"";
        getTextIn(firstSem12);
    }

    public void firstSem13(){
        chapter = "firstSem13";
        String firstSem13 = "\"YOU KIDS DON'T REALIZE HOW GOOD YOU HAVE IT,\" he informs you. \"WHEN I WAS GROWING UP, WE HAD MUCH WORSE BEASTS. STINKIER" +
                " FUR. MORE TEETH. AND DOWN SOUTH, OUT IN THE BOONIES, THOSE ARE WHERE YOU FIND THE ACTUAL BEASTS. THIS IS A PROGRESSIVE CAMPUS - THERE " +
                "SIMPLY CAN'T BE MUCH OF A PROBLEM HERE.\" He pauses, thinking. \"NOW, I DON'T WANT TO HEAR ANY MENTION OF THIS IN THE PAPERS, OKAY? " +
                "YOU DON'T WANT TO END UP LIKE CRAZY JOE.\" His secretary is guiding you out before you even get a chance to respond, and you find yourself " +
                "outside the office door, shocked. No problem here?\n\t" +
                "As you walk back to pack your things, you run into Crazy Joe, who is shaking his head sadly. \"The beast, is, at best, a pretty " +
                "clunky metaphor. You could have been more creative.\" You stare, and he beckons you closer. \"Listen. Listen. What if...the" +
                " mitochondria...is NOT the powerhouse of the cell?\n\t" +
                "You ignore him and walk on. You've got three and a half more years, and you're going to get to the root of this problem, whatever it takes.\n\n" +
                "Or maybe you can just transfer.\n\t";
        getTextIn(firstSem13);
    }

    //-----------------------FINALE TEXT-----------------------\\

    public void finale(){
        chapter = "finale";
        student.adjustDebt();
        String finale = "----------------------------------------------------\n" +
                "Thanks for playing! Here are your final stats:\n   Name: "+student.getName()+"\n   Major: "+student.getMajor()+
                "\n   Minor: "+student.getMinor()+"\n   Goals: "+student.goalToString()+"\n   Friends: "+student.friendToString()+
                "\n   Grades: "+student.getGrade()+"/100\n   " +
                "Stress: "+student.getStress()+"/10\n   "+"Sleep Deprivation: "+student.getSleepDepriv()+"/10\n   "+"Prestige: "+
                student.getPrestige()+"/10\n   Debt: "+student.getDebt()+"\n\t";
        finale+="Now, ready to go for another seven semesters?\t";
        getTextIn(finale);
    }

    //-----------------------INPUT HANDLER-----------------------\\

    public void inputHandler(){
        String rawInput = userInput();
        String input = (rawInput.substring(1, rawInput.length())).trim();
        String[] gameCommands = {"save","quit","help"};
        String[] genericCommands = {"look","talk","speak","walk","move","go","read","lift","touch","pick","pick up",
        "listen","smell","taste","eat","sit","stand","climb"};

        boolean runGame = true;
        for (int i=0;i<gameCommands.length;i++) {
            if (input.toLowerCase().contains(gameCommands[i].toLowerCase())) {
                runGame = false;
            }
        }
        //checks to see if user wants to save, quit, or get help
        if (!runGame) {
            //do stuff
        }

        //if not, run game event
        else {
            //-----------------------INTRO INPUT HANDLER-----------------------\\

            if (chapter.equals("intro1")) {
                if (stringNotEmpty(input)) {
                    if (stringOkLength(0, 30, input)) {
                        student.setName(input.toUpperCase());
                        intro2();
                    }
                }
            } else if (chapter.equals("intro2")) {
                String[] acceptableEntries = {"M", "F", "O"};
                if (stringOk(acceptableEntries, input.toUpperCase())) {
                    student.setGender(input.toUpperCase());
                    intro3();
                }
            } else if (chapter.equals("intro3")) {
                int intInput = Integer.parseInt(input);
                if (intWithinRange(1, 5, intInput)) {
                    student.setWealth(intInput);
                    intro4();
                }
            } else if (chapter.equals("intro4")) {
                int intInput = Integer.parseInt(input);
                if (intWithinRange(0, 100, intInput)) {
                    student.setGrades(intInput);
                    if (intInput < 60) {
                        intro5a();
                    } else if (60 <= intInput && intInput <= 80) {
                        intro5b();
                    } else
                        intro5c();
                }
            } else if (chapter.equals("intro5")) {
                String[] acceptableEntries = {"Ritzpaulroy", "Iliad", "Freedom", "Corple", "TC2"};
                if (stringOk(acceptableEntries, input)) {
                    College tempCollege;

                    //checks to see if user wants information about the colleges
                    if (input.toLowerCase().contains("info")) {
                        //initializes integer to select college; -1 is null
                        int tempInt = -1;
                        if (input.toLowerCase().contains("ritzpaulroy")) {
                            tempInt = 0;
                        } else if (input.toLowerCase().contains("iliad")) {
                            tempInt = 1;
                        } else if (input.toLowerCase().contains("freedom")) {
                            tempInt = 2;
                        } else if (input.toLowerCase().contains("corple")) {
                            tempInt = 3;
                        } else if (input.toLowerCase().contains("tc2")) {
                            tempInt = 4;
                        }

                        tempCollege = new College(tempInt);
                        textArea.append("------------------------------------------------------------------\n");
                        textArea.append(tempCollege.toString() + "\n");
                        textArea.append("------------------------------------------------------------------\n");
                        textArea.append(tempCollege.moreOptions() + "\n");
                    }

                    //if user does not want info, set selected college as their college
                    else {
                        //initializes integer to select college; -1 is null
                        int tempInt = -1;
                        if (input.toLowerCase().contains("ritzpaulroy")) {
                            tempInt = 0;
                        } else if (input.toLowerCase().contains("iliad")) {
                            tempInt = 1;
                        } else if (input.toLowerCase().contains("freedom")) {
                            tempInt = 2;
                        } else if (input.toLowerCase().contains("corple")) {
                            tempInt = 3;
                        } else if (input.toLowerCase().contains("tc2")) {
                            tempInt = 4;
                        }

                        student.setCollege(tempInt);
                        intro6();
                    }
                }
            } else if (chapter.equals("intro6")) {
                clear();
                summer1();
            }

            //-----------------------SUMMER INPUT HANDLER-----------------------\\

            else if (chapter.equals("summer1")) {
                HashMap<String, String[]> validMap = new HashMap<String, String[]>();
                validMap.put("couch", new String[]{"look","walk","move","go","sit","use"});
                validMap.put("phone", new String[]{"look","walk","move","go","pick","talk","speak","use","call"});
                validMap.put("mom", new String[]{"look","talk","speak","walk","move","go"});
                validMap.put("window", new String[]{"look","walk","move","go"});

                if (input.toLowerCase().equals("look") || input.toLowerCase().equals("look around")) {
                    textArea.append("Your living room's pretty standard. Your mom is sitting on a couch to your right, " +
                            "reading the paper. There's a window to the left of her, and and old house phone on a table to her " +
                            "right.\n\n>");
                }
                else {
                    if (objectActionMap(validMap, input)) {
                        //couch actions
                        if (input.toLowerCase().contains("couch")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("It's a couch. Not much to see.\n\n>");
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You walk over to the couch. Your mom looks up at you.\n\n>");
                            else if (input.toLowerCase().contains("sit") || input.toLowerCase().contains("use"))
                                textArea.append("You sit on the couch. Your mom looks up at you.\n\n>");
                        }
                        //phone actions
                        else if (input.toLowerCase().contains("phone")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("It's a phone like any other. You could use it to call a friend, if you set your mind to it.\n");
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You walk over to the phone.\n\n>");
                            else if (input.toLowerCase().contains("pick") || input.toLowerCase().contains("use") || input.toLowerCase().contains("talk")
                        || input.toLowerCase().contains("speak") || input.toLowerCase().contains("use") || input.toLowerCase().contains("call"))
                                textArea.append("You pick up the phone and call one of your friends. You chat a little bit about how you " +
                                        "are both leaving home next week (how crazy is that?). And that you guys will totally still talk " +
                                        "all the time and hang out. Obviously.\n\n>");
                        }
                        //mom actions
                        if (input.toLowerCase().contains("mom")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("Your mom's sitting peacefully on the couch, reading. She's so great.\n\n>");
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You walk over to the couch. Your mom looks up at you.\n\n>");

                            //THIS ACTION CLEARS USER FOR NEXT CHAPTER
                            else if (input.toLowerCase().contains("talk") || input.toLowerCase().contains("speak")) {
                                textArea.append("\"Hey, mom!\" you say. She looks at you a little strangely, since you've " +
                                        "been standing silently in the living room until just now. \"I can't believe I'm leaving" +
                                        " next week\", you confide. \"We're going to miss you!\" she laments.\n\n");

                                if (student.getWealth() < 3) {
                                    summer2a();
                                    student.setParentContrib(0);
                                }
                                else if (student.getWealth() >= 3 && student.getWealth() < 5) {
                                    summer2b();
                                    student.setParentContrib(1);
                                }
                                else if (student.getWealth() == 5) {
                                    summer2c();
                                    student.setParentContrib(2);
                                }
                            }
                        }
                        //couch actions
                        if (input.toLowerCase().contains("window")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("Through the window, you can see the playground equipment you " +
                                        "used to play on. You're too old and mature for such things now. Down with" +
                                        " fun and childlike wonder. Up with fiscal responsibility.\n\n>");
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You walk over to the window. Rain is falling gently. You could 'look " +
                                        "through the window', maybe.\n\n>");
                        }
                        validMap.clear();
                    }
                }
            }

            else if (chapter.equals("summer2")){
                if (stringNotEmpty(input))
                    summer3();
            }

            else if (chapter.equals("summer3")){
                if (stringNotEmpty(input))
                    summer4();
            }

            else if (chapter.equals("summer4")){
                clear();
                orientation1();
            }

            //-----------------------ORIENTATION INPUT HANDLER-----------------------\\

            else if (chapter.equals("orientation1")){
                System.out.println(len);
                System.out.println(index);
                HashMap<String, String[]> validMap = new HashMap<String, String[]>();

                validMap.put("club", new String[]{"look","walk","move","go","touch","talk","speak","ask"});
                validMap.put("tables", new String[]{"look","walk","move","go","touch","talk","speak","ask"});
                validMap.put("person", new String[]{"look","talk","speak","walk","move","go","touch","ask"});
                validMap.put("member", new String[]{"look","talk","speak","walk","move","go","touch","ask"});
                validMap.put("guide", new String[]{"look","talk","speak","walk","move","go","ask","join"});
                validMap.put("tour", new String[]{"look","talk","speak","walk","move","go","ask","join"});
                validMap.put("students", new String[]{"look","talk","speak","walk","move","go","touch","ask"});
                validMap.put("squirrel", new String[]{"look","go","walk","move","touch","catch","pet"});

                if (input.toLowerCase().equals("look") || input.toLowerCase().equals("look around") || input.toLowerCase().equals("")) {
                    textArea.append("The quad is a hive of activity. A group of students nervously mill around, " +
                            "at a loss of what to do in this crowd of strangers. On one side of the path, a collection" +
                            " of tables are set up, staffed by earnest-looking club members. On the other, a tour " +
                            "guide smiles brightly at you - he's collecting a herd of students, and the tour " +
                            "looks like it will leave soon. Under the tree to your right sits a squirrel, " +
                            "seemingly unperturbed by the hubbub.\n\n>");
                }
                else {
                    if (objectActionMap(validMap, input)) {
                        //club table actions
                        if (input.toLowerCase().contains("table") || input.toLowerCase().contains("club") || input.toLowerCase().contains("person") || input.toLowerCase().contains("member")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You peer over at the tables. A club member smiles at you, a little too brightly.\n\n>");
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You wander over to the tables, trying to appear casually interested." +
                                        "\"Hello!\" one of the members calls out. You're obligated to talk to them now.\n\n>");
                            else if (input.toLowerCase().contains("touch"))
                                textArea.append("You walk up to a table, then lean in and gently caress the club " +
                                        "member's face. You hear a quiet \"what the fuck?\" Not making many friends yet," +
                                        " are you?\n\n>");
                            else if (input.toLowerCase().contains("talk") || input.toLowerCase().contains("speak") || input.toLowerCase().contains("ask")) {
                                textArea.append("\"Oh, you're interested in hearing more about our ");
                                orientation1a();
                            }
                        }
                        //tour guide actions
                        if (input.toLowerCase().contains("guide") || input.toLowerCase().contains("tour"))
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You survey the tour group. The tour guide beckons you over.\n\n>");
                            //THIS ACTION CLEARS USER FOR NEXT CHAPTER
                            else {
                                textArea.append("\"Thanks for joining us!\" the tour guide says. \"I think our group's just about full, so" +
                                        " if you'll follow me...\" ");
                                orientation2();
                            }
                        //student actions
                        if (input.toLowerCase().contains("students")){
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You look nervously at the other students. They look nervously back.\n\n>");
                            //launches a minigame
                            else if (input.toLowerCase().contains("walk") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go") || input.toLowerCase().contains("speak") || input.toLowerCase().contains("talk")) {
                                textArea.append("Screwing up your courage, you walk over to the students. \"Hey,\" you call out, trying " +
                                        "to be cool and casual. You were definitely not cool and casual in high school, but college is " +
                                        "when you get cool, right? \"I'm " + student.getName() + ", what's your name?\"\n\n>");
                                orientation1b();
                            }

                        }
                        //squirrel actions
                        if (input.toLowerCase().contains("squirrel")) {
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You look at the squirrel. The squirrels glares back at you. It looks like it's seen " +
                                        "some shit.\n\n>");
                            else if (input.toLowerCase().contains("go") || input.toLowerCase().contains("walk") || input.toLowerCase().contains("move"))
                                textArea.append("You began walking slowly toward the squirrel, making quiet hushing noises. Why are you doing " +
                                        "this? Who knows, you weirdo. The squirrel flicks its tail nervously, but stays where it is.\n\n>");
                            else if (input.toLowerCase().contains("touch") || input.toLowerCase().contains("pet") || input.toLowerCase().contains("catch")) {
                                textArea.append("You kneel down and gently reach out a hand toward the squirrel. It looks at you for a moment, then " +
                                        "runs up your arm to perch on your shoulder. People are staring, but that's ok, you've made a friend! Maybe" +
                                        " you can even make some human friends someday.\n\n>");
                                student.addFriend("a squirrel");
                            }
                        }
                    }
                }
                validMap.clear();
            }

            else if (chapter.equals("orientation1a")){
                String[] acceptableEntries = {"y", "n", "yes", "no"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("n") || input.toLowerCase().contains("no"))
                        textArea.append("\"Fine,\" the club member pouts. \"If you're gonna be like that, fine. " +
                                "We'll see who's laughing when you have to list extracurriculars on job applications.\"\n");
                    else if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes"))
                        textArea.append("\"Super! You'll get a ton of emails, but feel free to delete them immediately. " +
                                "Also, don't worry about actually coming to meetings. You can list this under extracurriculars " +
                                "on job applications anyway.\"\n");
                    chapter = "orientation1";
                    inputHandler();
                }
            }

            else if (chapter.equals("orientation1b")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")){
                    orientation1c();
                }
            }

            else if (chapter.equals("orientation1c")){
                //launches friend mini game - roll dice for weird/boring
                myGame = new SmallTalk();

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            myGame.requestFocus();
                            ArrayList<String> friends = myGame.gamePlay();
                            if (friends.size()==0){
                                textArea.append("Oh, bummer, looks like you haven't made any friends yet. I'm sure it will happen.\n\n");
                            }
                            else if (friends.size()==1) {
                                textArea.append("Wowza, you made an entire friend! Well done! "+friends.get(0)+" has been added to your " +
                                        "friends list. Don't make it weird now.\n\n");
                            }
                            else {
                                textArea.append("Look at you, so popular, you made multiple friends! Well done! They have been added to your " +
                                        "friends list. Don't make it weird now.\n\n");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else if (chapter.equals("orientation2")){
                String[] acceptableEntries = {"humanities","science"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    student.setMajorMinor(input.toUpperCase(),"");
                    orientation3();
                }
            }

            else if (chapter.equals("orientation3")){
                String[] acceptableEntries = {"yes","no","y","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes"))
                        orientation3a();
                    else if (input.toLowerCase().contains("n") || input.toLowerCase().contains("no"))
                        orientation3b();
                }
            }

            else if (chapter.equals("orientation3a") || chapter.equals("orientation3b")){
                if (chapter.equals("orientation3a"))
                    student.setMajorMinor(student.getMajor(),input.toUpperCase());
                orientation4();
            }

            else if (chapter.equals("orientation4")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("discover your passions");
                }
                orientation4a();
            }
            else if (chapter.equals("orientation4a")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("make lifelong friends");
                }
                orientation4b();
            }
            else if (chapter.equals("orientation4b")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("get involved");
                }
                orientation4c();
            }
            else if (chapter.equals("orientation4c")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("get invited to parties");
                }
                orientation4d();
            }
            else if (chapter.equals("orientation4d")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("learn real world skills");
                }
                orientation4e();
            }
            else if (chapter.equals("orientation4e")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("slay the beast");
                }
                orientation4f();
            }
            else if (chapter.equals("orientation4f")){
                if (input.toLowerCase().contains("y") || input.toLowerCase().contains("yes")) {
                    student.addGoal("graduate on time");
                }
                orientation5();
            }
            else if (chapter.equals("orientation5")){
                clear();
                intermission1();
            }

            //-----------------------INTERMISSION INPUT HANDLER-----------------------\\
            else if (chapter.equals("intermission1")){
                if (input.toLowerCase().contains("one") || input.toLowerCase().contains("1")) {
                    intermission2();
                }
            }
            else if (chapter.equals("intermission2")){
                if (input.toLowerCase().contains("four") || input.toLowerCase().contains("4")) {
                    intermission3();
                }
            }
            else if (chapter.equals("intermission3")){
                clear();
                firstSem1();
            }
            //-----------------------FIRST SEMESTER INPUT HANDLER-----------------------\\
            else if (chapter.equals("firstSem1")){
                String[] acceptableEntries = {"info"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    textArea.append(student.getRmDesc()+"\n\n");
                    firstSem2();
                }
            }

            else if (chapter.equals("firstSem2")){
                String[] acceptableEntries = {"yes","y","no","n","go","stay"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y") || input.toLowerCase().contains("go")) {
                        //going to get food with your roommate
                        firstSem2a();
                    } else
                        //not going to get food with your roommate
                        firstSem2b();
                }
            }

            //not going to get food with your roommate
            else if (chapter.equals("firstSem2b")){
                firstSem3();
            }

            //going to get food with your roommate
            else if (chapter.equals("firstSem2a")){
                String[] acceptableEntries = {"pizza","mexican"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    firstSem2a2();
                }
            }
            else if (chapter.equals("firstSem2a2")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")){
                        //talking to food employee
                        textArea.append("You turn toward the worker, waiting for him to say what's on his mind. \"Oh, they're evil,\"" +
                                "he says, \"but unfortunately not made up. The things I've seen in the kitchen...I'd avoid the " +
                                "hot dogs, if I were you.\"\n\n");
                        firstSem2a3();
                    }else{
                        //not talking to food employee
                        textArea.append("You choose to keep quietly forcing down your food, not engaging the staff member. " +
                                "Your mom told you to not talk to strangers, after all.\n\n");
                        firstSem3();
                    }
                }
            }

            else if (chapter.equals("firstSem2a3")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")){
                        //taking job
                        firstSem2a4();
                    }else{
                        //not taking job
                        firstSem2a5();
                    }
                }
            }

            //taking job
            else if (chapter.equals("firstSem2a4")){
                String[] acceptableEntries = {"5","five","10","ten","15","fifteen","20","twenty"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("5") || input.toLowerCase().contains("five")){
                        student.setJob(1);
                        textArea.append("\"Great, I have you down for five hours! Can you start next week?\"");
                    }else if (input.toLowerCase().contains("10") || input.toLowerCase().contains("ten")){
                        student.setJob(2);
                        textArea.append("\"Great, I have you down for ten hours! Can you start next week?\"");
                    }else if (input.toLowerCase().contains("15") || input.toLowerCase().contains("fifteen")){
                        student.setJob(3);
                        textArea.append("\"Great, I have you down for fifteen hours! Can you start next week?\"");
                    }else if (input.toLowerCase().contains("20") || input.toLowerCase().contains("twenty")){
                        student.setJob(4);
                        textArea.append("\"Great, I have you down for twenty hours! Can you start next week?");
                    }
                    student.upPrestige();
                }else
                    textArea.append("\"Oh, if you've changed your mind, no worries. ");
                firstSem2a5();
            }

            //walking back home with roommate
            else if (chapter.equals("firstSem2a5")){
                firstSem2a6();
            }

            //walking back home with roommate - friend determination
            else if (chapter.equals("firstSem2a6")){
                String[] acceptableEntries = {"yes","no","y","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to being friends
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("Your roommate smiles. \"Super! We should probably head back to the room.\"");
                        student.addFriend(student.getRmName());
                    }

                    //say no to being friends
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("Your roommate frowns. \"Fine. We should probably head back to the room.\"");
                    }
                    textArea.append("You walk on, trying to not be thrown off by that unnerving conversation.");
                }
            }

            else if (chapter.equals("firstSem3")){
                clear();
                firstSem4();
            }

            else if (chapter.equals("firstSem4")){
                textArea.append("Yeah, what the hell? This wasn't on College Confidential.\n\n");
                firstSem5();
            }

            else if (chapter.equals("firstSem5")){
                HashMap<String, String[]> validMap = new HashMap<String, String[]>();

                validMap.put("email", new String[]{"reply","read","delete"});
                validMap.put("roommate", new String[]{"wake","talk","speak","ask","walk","go","touch","move"});
                validMap.put(student.getRmName(), new String[]{"wake","talk","speak","ask","walk","go","touch","move"});
                validMap.put("dave", new String[]{"look","talk","touch","speak"});
                validMap.put("poster", new String[]{"look","talk","touch","speak"});

                //THIS ACTION CLEARS STUDENT FOR NEXT CHAPTER
                if (input.toLowerCase().contains("sleep") || input.toLowerCase().contains("bed")) {
                    textArea.append("You decide to not worry about the beast at the moment, and instead crawl into bed, going under the " +
                            "covers where you are safe. You can worry about stuff in the morning...\n\n");
                    firstSem6();
                }
                else if (input.toLowerCase().equals("look") || input.toLowerCase().equals("look around") || input.toLowerCase().equals("")) {
                    textArea.append("Your room is quiet and peaceful, ominous emails aside. The only illuminations are the glow of your laptop " +
                            "and your RA-approved Christmas lights. Your roommate sleeps soundly, and loudly, on their bed - and your own bed " +
                            "calls to you invitingly. On the wall above you, Dave Grohl stares lovingly down.\n\n>");
                }
                else {
                    if (objectActionMap(validMap, input)) {
                        //email actions
                        if (input.toLowerCase().contains("email")) {
                            if (input.toLowerCase().contains("reply"))
                                textArea.append("You try to reply to the email, asking about the beast, but you immediately get a computer-" +
                                        "generated no-reply response. It was worth a try.\n\n>");
                            else if (input.toLowerCase().contains("read") || input.toLowerCase().contains("move") || input.toLowerCase().contains("go"))
                                textArea.append("You look at your other emails to distract yourself, but there are no new ones.\n\n>");
                            else if (input.toLowerCase().contains("delete"))
                                textArea.append("You delete the email. Out of sight, out of mind, right?\n\n>");
                                student.increaseCluelessness();
                            }
                        }
                        //roommate actions
                        if (input.toLowerCase().contains("roommate") || input.toLowerCase().contains(student.getRmName()))
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You try your darndest to wake up your roommate, but they're sound asleep.\n\n>");
                            }
                        //poster actions
                        if (input.toLowerCase().contains("poster") || input.toLowerCase().contains("dave")){
                            if (input.toLowerCase().contains("look"))
                                textArea.append("You look fondly up at Dave Grohl, and he looks fondly back.\n\n>");
                            else if (input.toLowerCase().contains("talk") || input.toLowerCase().contains("speak"))
                                textArea.append("You try asking Dave Grohl if he knows anything about the beast, but he doesn't respond." +
                                        " He's a poster.\n\n>");
                            else if (input.toLowerCase().contains("touch"))
                                textArea.append("You reach up to stroke Dave Grohl's face. He'd know what to do.\n\n>");
                        }
                validMap.clear();
            }

            //go to class
            else if (chapter.equals("firstSem6")){
                clear();
                firstSem7();
            }

            //after class
            else if (chapter.equals("firstSem7")){
                String[] acceptableEntries = {"yes","no","y","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to talking to the professor
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("You walk up to the front of the room after class - the professor looks up with a trace of irritation. \"Yes?\"" +
                                "she says. You haltingly ask about the campus beast.");
                        firstSem7a();
                    }

                    //say no to talking to the professor
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("You decide not to talk to her. You'll figure it out sooner or later.\n\n");
                        student.increaseCluelessness();
                        firstSem8();
                    }
                }
            }

            else if (chapter.equals("firstSem7a")){
                clear();
                firstSem8();
            }

            else if (chapter.equals("firstSem8")){
                String[] acceptableEntries = {"yes","no","y","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to taking break
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("Hell yeah you do. You hit up Facebook for a while, then watch an episode of Parks & Rec on Netflix (it's " +
                                "fine, they're only twenty minutes! (your sleep deprivation is increasing...)\n\n");
                        student.upSleepDepriv();
                    }

                    //say no to taking break
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("No. You're a hard worker. You buckle down and bang out a couple pages.\n\n");
                    }
                    firstSem8a();
                }
            }

            else if (chapter.equals("firstSem8a")){
                String[] acceptableEntries = {"yes","no","y","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to taking break
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("Sweet! You go get pizza with them. (your sleep deprivation is increasing...)\n\n");
                        student.upSleepDepriv();
                    }

                    //say no to taking break
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("No. You're a hard worker. You buckle down and bang out a couple pages.\n\n");
                    }
                    firstSem8b();
                }
            }

            else if (chapter.equals("firstSem8b")){
                String[] acceptableEntries = {"sleep","bed","work","paper","stay","finish"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to going to bed
                    if (input.toLowerCase().contains("sleep") || input.toLowerCase().contains("bed")) {
                        textArea.append("You fall asleep immediately, exhausted. (Your sleep deprivation decreases, " +
                                "but your grades are dropping...\n\n");
                        student.downSleepDepriv();
                        student.downGrade();
                        student.upStress();
                    }
                    //say no to going to bed
                    else if (input.toLowerCase().contains("work") || input.toLowerCase().contains("paper") || input.toLowerCase().contains("stay") || input.toLowerCase().contains("finish")){
                        textArea.append("You stay up and finish it - you're exhausted, but it's done. " +
                                "Who needs sleep, anyway?\n\n");
                        student.upGrade();
                        student.upSleepDepriv();
                    }
                    firstSem8c();
                }
            }

            else if (chapter.equals("firstSem8c")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //say yes to believing in the beast
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("You do believe it exists, and you say so. The boy frowns at you, but doesn't say anything further.\n\n");
                    }

                    //say no to believing in the beast
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("Nah, he's probably right. You've never seen it, either.\n\n");
                        student.increaseCluelessness();
                    }
                    firstSem8d();
                }
            }

            else if (chapter.equals("firstSem8d")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //ask about the shotgun
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("You ask about the shotgun, obviously. \"Is that in case you get attacked by the beast?\".");
                        firstSem8e();
                    }

                    //don't ask about the shotgun
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("Nah, you're sure there's a good reason for it. Unperturbed, you flop down on your bed for a quick nap.\n\n");
                        student.increaseCluelessness();
                        firstSem9();
                    }
                }
            }

            else if (chapter.equals("firstSem8e")){
                firstSem9();
            }

            else if (chapter.equals("firstSem9")){
                clear();
                firstSem10();
            }

            else if (chapter.equals("firstSem10")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //go help your roommate
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("You have to go help your roommate, project be damned - what if they're actually in trouble? You jam your " +
                                "laptop in your backpack and grab your coffee, making your way back to the room at a brisk walk.\n\n");
                        student.downGrade();
                        firstSem10a();
                    }

                    //don't go help your roommate
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("You hesitate - what if your roommate is really in trouble? - but eventually switch your phone to silent. You really, " +
                                "really have to get this project done by tomorrow. Your GPA doesn't define you as a person, of course - but you're going to " +
                                "finish this. Because your GPA defines you as a person.\n\n");
                        student.increaseCluelessness();
                        student.upStress();
                        student.upGrade();
                        firstSem10b();
                    }
                }
            }

            //try to help your roommate
            else if (chapter.equals("firstSem10a")){
                String[] acceptableEntries = {"fight","help","save","nothing"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("nothing")) {
                        textArea.append("You hang back against the wall like the coward you are");
                        //UNSUCCESSFUL IN HELPING
                        firstSem10c();
                    }
                    else {
                        //if student's too sleep-deprived, stressed, or clueless, can't save roommate
                        if (student.getStress() > 5 || student.getSleepDepriv() > 5){
                            textArea.append("Heroically, you charge the beast and start trying to fight it. However, you are too " +
                                    "stressed out and sleep-deprived, and your reaction time is really slow. The beast easily pushes you away, " +
                                    "and disappears out of the window, clutching your poor roommate.\n\n");
                            //UNSUCCESSFUL IN HELPING
                            firstSem10c();
                        }
                        else if (student.getClueless() > 5) {
                            textArea.append("Unfortunately, since you've spent your time ignoring the beast's existence, you don't have the tools" +
                                    " to properly fight it. It easily pushes you away, and disappears out of the window, clutching your poor" +
                                    " roommate.\n\n");
                            //UNSUCCESSFUL IN HELPING
                            firstSem10c();
                        }
                        else{
                            textArea.append("You charge at the beast and somehow manage to fight it off. Your roommate escapes its grasp, and the beast" +
                                    " suddenly seem to realize it's outnumbered. It escapes out the window into the night.\n\n");
                            //SUCCESSFUL IN HELPING
                            firstSem10d();
                        }
                    }
                }
            }
            //don't try to help your roommate
            else if (chapter.equals("firstSem10b")){
                firstSem10c();
            }

            //whether you help your roommate or not, this runs
            else if (chapter.equals("firstSem10c") || chapter.equals("firstSem10d")){
                firstSem11();
            }

            //choosing to meet with president
            else if (chapter.equals("firstSem11")){
                String[] acceptableEntries = {"yes","y","no","n"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    //go to meeting
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y")) {
                        textArea.append("You decide you should probably go talk to him - you did get into a physical altercation with a fantasy " +
                                "creature, after all, and you'd like a little closure. You spend the day packing your stuff (whatever wasn't " +
                                "ruined in the fight) and then make your way down to the president's office.\n\n");
                        firstSem12();
                    }

                    //don't go to meeting
                    else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("Nah - you're busy enough as it is, and you've done a good enough job avoiding your problems this semester. " +
                                "You can keep it up for another three and a half years...right?\n\nAs you walk back to pack your things, you run" +
                                " into Crazy Joe, who is shaking his head sadly. \"The beast, is, at best, a pretty clunky metaphor. You could" +
                                " have been more creative.\" You stare, and he beckons you closer. \"Listen. Listen. What if...the mitochondria..." +
                                "is NOT the powerhouse of the cell?\n\n");
                        student.increaseCluelessness();
                        finale();
                    }
                }
            }
            else if (chapter.equals("firstSem12")){
                String[] acceptableEntries = {"no","yes","n","y","beast","kill","fight","bribe"};
                if (stringOk(acceptableEntries, input.toLowerCase())) {
                    if (input.toLowerCase().contains("yes") || input.toLowerCase().contains("y") || input.toLowerCase().contains("money")) {
                        textArea.append("\"I CAN...DIRECT YOU TO A NICE SCHOLARSHIP FOR YOUR TROUBLES. YES, I KNOW OUR FINANCIAL AID OFFICE HAS " +
                                "UNFORTUNATELY BEEN CUT, BUT THERE'S SOME MONEY IF YOU KNOW THE RIGHT PEOPLE.\"\n\n");
                    }else if (input.toLowerCase().contains("no") || input.toLowerCase().contains("n")) {
                        textArea.append("\"I CAN...DIRECT YOU TO A NICE SCHOLARSHIP FOR YOUR TROUBLES. YES, I KNOW OUR FINANCIAL AID OFFICE HAS " +
                                "UNFORTUNATELY BEEN CUT, BUT THERE'S SOME MONEY IF YOU KNOW THE RIGHT PEOPLE.\"\n\n");
                    }else if (input.toLowerCase().contains("beast") || input.toLowerCase().contains("kill") || input.toLowerCase().contains("fight")){
                        textArea.append("\"AH, YOU SEE, THAT'S SIMPLY NOT WITHIN OUR CURRENT GOALS. WE'RE IN THE MIDDLE OF IMPLEMENTING " +
                                "SOME VERY IMPORTANT PROGRAMS THAT TAKE PRECEDENCE. FIGHTING THE BEAST WOULD BE MORE A JOB FOR YOU, THE " +
                                "STUDENTS. THIS IS YOUR CAMPUS! TAKE CHARGE!\"\n\n");
                    }
                    firstSem13();
                }
            }

            else if (chapter.equals("firstSem13")){
                finale();
            }

            //closes window at the end of the game
            else if (chapter.equals("finale")){
                dispose();
            }
        }
    }
}