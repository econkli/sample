/**
 * Created by Emily on 10/5/15.
 */
public class Roommate {
    private String rmName;
    private String rmDesc;
    private String rmGender;

    //sets roommate information via random integer
    public Roommate (int rmSelect){

        if (rmSelect==0){
            rmName = "Molly";
            rmGender = "F";
            rmDesc = "Molly is a little weird and kind of nerdy, but in a cool way. Not like the other girls. She seems like the type " +
                    "who might have a Tumblr and wear flower crowns casually. You can overlook that, though - the two of you should get along fine.";
        }
        else if (rmSelect==1){
            rmName = "Janie";
            rmGender = "F";
            rmDesc = "Janie seems very shy - you have to lean forward a bit to hear her as she introduces herself." +
                    "She is quiet, sweet, and very Christian - she probably won't be annoying, but you'll" +
                    "have to be careful not to offend her.";
        }
        else if (rmSelect==2){
            rmName = "Diamond";
            rmGender = "F";
            rmDesc = "Diamond is a double major in theatre and performance flute (how were those majors available, you ask?. " +
                    "don't worry about it. It's in the syllabus)." +
                    "She is a little eccentric but seems very friendly. The two of you should " +
                    "get along just fine, provided her musical endeavors don't get on your nerves.";
        }
        else if (rmSelect==3){
            rmName = "Jim";
            rmGender = "M";
            rmDesc = "Jim introduces himself as a Science major. You're not sure what type of science, " +
                    "but the textbooks he's carrying seem very serious. Jim thinks he's a cool nerd but " +
                    "is really just a regular nerd. He's a quiet sort but might keep you up with his " +
                    "late-night Super Smash Bros sessions.";
        }
        else if (rmSelect==4){
            rmName = "???";
            rmGender = "O";
            rmDesc = "Your roommate is incredibly mysterious. You're not sure of his or her name, and you know " +
                    "nothing about them, except that their fashion sense is wicked cool and you're super jealous.";
        }
        else if (rmSelect==5){
            rmName = "Lance";
            rmGender = "M";
            rmDesc = "Lance is a large hipster with gauges, beanie hat, and lots of feelings. " +
                    "He's an Humanities major and might coerce you into reading his bad poetry - be wary.";
        }
    }

    //return roommate info
    public String getRmName(){ return rmName; }

    public String getRmGender(){ return rmGender; }

    public String getRmDesc(){ return rmDesc; }
}
