import java.util.*;


/**
Program to model a game of medieval times where world is filled with warriors and nobles. 
In this program the nobles can fight or hire the protectors to battle against
another noble.The noble with less strength dies, and the noble with
more strength will have a decrease in strength. The protectors can be archers, swordsmen or wizards
@author : Sahitya Pavurala
//ID : 0490373
//HW 04
*/

/** Noble is the abstract class which is inherited by both PersonWithStrength and Lord classes*/
abstract class Noble
{
	/** name is the member variable of Noble which is of type string which holds the name of the noble*/
	private String name;
	/** alive is the member variable of Noble which is a boolean value to show if the noble is alive or not*/
	private boolean alive = true;
	/** Noble(String name) is a single argument constructor which initialises the name	
	 @param		name				name of the Noble given as a parameter
	 */
	Noble(String name)
	{
		this.name = name;
	}
	
	/** battle(Noble opponent) is called when a noble want to battle against anothe noble	
	 @param		opponent		it is the noble against whom the battles should happen
	 */
	void battle(Noble opponent)
	{
		System.out.println(this.name + " battles " + opponent.name );
		if	(opponent.alive == false && this.alive == false)//checks if both the nobles are already dead
		{System.out.println("Oh, NO!  They're both dead!  Yuck! ");}
		if (this.alive == false)//checks if the noble is already dead
		{System.out.println("He's dead, " + opponent.name);}
		else if (opponent.alive == false)//checks if the opponent is dead
		{System.out.println("He's dead, " + this.name);}
		else if(this.getNobleStrength() < opponent.getNobleStrength())//checks if the noble is noble has less strength than the opponent
		{	
			this.attack(opponent);//calls to appropriate attack method in PersonWithStrength or Lord
			opponent.attack(this);
			opponent.defeats(this);//call to the defeats method in noble
			this.die();//calls the die mentod of the noble with less strength
		}
		else if(this.getNobleStrength() > opponent.getNobleStrength())//checks if the noble is noble has more strength than the opponent
		{	
			this.attack(opponent);
			opponent.attack(this);
			this.defeats(opponent);				
			opponent.die();
		}
		else//checks the other condition that is if both the noble and opponent have the same strength	
		{			
			this.attack(opponent);
			opponent.attack(this);
			this.alive = false;
			opponent.alive = false;
			this.die();
			opponent.die();
			System.out.println("Mutual Annihilation: " + this.name + " and " + opponent.name + " die at each other's hands ");
		}
	}
	
	/** defeats(Noble opponent) is the method which is called when the noble with greater strength
		in a battle wins against other noble
	 @param		opponent			the noble who gets defeated 
	 */	
	void defeats(Noble opponent)
	{
		//warrior strength is decreased by calling the decreaseStrength(double) method of the warrior
		System.out.println(this.name + " defeats " + opponent.name );
		decreaseStrength(opponent.getNobleStrength() / this.getNobleStrength());
		opponent.alive = false ;
	}
	
	/** getLifeStatus() is the method which gives the status if the noble to say of he is
		alive or not
	 */	
	boolean getLifeStatus(){return alive ;}
	
	/** getNobleName() is a getter method for returning the noble's name of type String	
	 @return	name		it is the name of the noble 		    
	 */
	String getNobleName(){return this.name;}
	
	/** getNobleStrength() is the abstract method which is implemented in boths PersonWithStrength and Lord classes
	 */
	abstract double getNobleStrength();
	
	/** decreaseStrength(double ratio) is the abstract method which is implemented in boths PersonWithStrength and Lord classes
	 */
	 abstract void decreaseStrength(double ratio);
	
	/** attack(Noble opponent) is the abstract method which is implemented in boths PersonWithStrength and Lord classes
	 */
	 abstract void attack(Noble opponent);
	
	/** die() is the abstract method which is implemented in boths PersonWithStrength and Lord classes
	 */
	abstract void die();
}




/** PersonWithStrength class is inherited from Noble class*/
class PersonWithStrength extends Noble
{	
	/** strength is the member variable of PersonWithStrength which is of type double which holds the strength of the PersonWithStrength*/
	private double strength;
	
	/**PersonWithStrength(String name, double strength) is a two argument constructor which initialises the name and strength	
	 @param		name				name of the PersonWithStrength given as a parameter
	 @param		strength			strength of the PersonWithStrength given as a parameter
	 */
	PersonWithStrength(String name, double strength)
	{
		super(name);//call to base class constructor
		this.strength = strength;
	}
	
	/** attack(Noble opponent) is the abstract method in the Noble class but in PersonWithStrength it has no impelmentation, but it is necessary
		because when we call the battle method we are not specifying whether he is a Lord or a PersonWithStrength  
	 */
	void attack(Noble opponent){}
	
	/** getNobleStrength() is a getter method for returning the PersonWithStrength's strength of type Double
	 @return	strength		it is the strength of the PersonWithStrength  		    
	*/
	double getNobleStrength(){ return strength ;}
	
	/** setNobleStrength(double strength) is a setter method for returning the PersonWithStrength's strength of type Double
	 @return	strength		it is the strength of the PersonWithStrength  		    
	*/
	void setNobleStrength(double strength){this.strength = strength ;}
	
	@Override //here we are overriding the toString method to display the PersonWithStrength information
	public String toString()
	{	StringBuilder result = new StringBuilder();
		result.append(this.getNobleName() + " : " + this.strength );
		return result.toString();
	}
	
	/** decreaseStrength(double ratio) is the method which is called when the nobles battle against each other and the strengths of 
		warriors should be decreased in a certain ratio
	 @param		ratio		it is the ratio based on which the strength is to be decreased 
	*/
	void decreaseStrength(double ratio)
	{
		strength *= (1-ratio);
	}
	
	/** die() is the method which is called when the PersonWithStrength battles against other Noble and if he loses the battle
	*/
	void die()
	{	
		this.strength = 0;
	}
}	


/** Lord class is inherited from Noble class*/
class Lord extends Noble 
{	
	/** army is the member variable of Lord which is an arraylist that holds protectors */
	private List<Protector> army = new ArrayList<Protector>();
	
	/**Lord(String name) is a single argument constructor which initialises the name	
	 @param		name				name of the PersonWithStrength given as a parameter
	 */
	Lord(String name)
	{
		super(name);//call to super class constructor
	}
	
	/**removeSoldierFromArmy(Protector soldier) is method of the lord class which is called when a Protector quits the army
	*/
	void  removeSoldierFromArmy(Protector soldier)
	{
		this.army.remove(soldier);
	}
	
	/** hire(Protector soldier) is the method to be called when the Lord wants to hire a protector 
	 @param		soldier			it is the protector which is to be added to the army of lord
	 */
	void hire(Protector soldier)
	{
		if( soldier.getMaster() == null && getLifeStatus() && soldier.getProtectorStrength() > 0)//check if noble and protector are alive and protector is not hired already
		{
			army.add(soldier);
			soldier.setMaster(this);
		}
		else
		{
			System.out.println(this.getNobleName() +" could not hire "+ soldier.getProtectorName());
		}
	}
	
	/** attack(Noble opponent) is the abstract method in the Noble class it is implemented in Lord class to make the all the
		protectors in the army fight
	@param	opponent		it is the Noble against which this battle is called 
	 */
	void attack(Noble opponent)
	{	
		for(Protector protector:army)
		{
			protector.fight();
		}
	}
	
	/** getNobleStrength() is the method which returns the strength of the lord, which is the 
		combined strength of all the protectors in his army
	 @return	strength		it is the total strength of the lord	    
	 */
	double getNobleStrength()
	{	
		double strength = 0;
		for(Protector soldier:army)
		{
			strength += soldier.getProtectorStrength();
		}
		return strength;
	}
	
	/** decreaseStrength(double ratio) is a  method for decresing the lords strength of type Double
	 @param		ratio		it is the ratio by which the strength is to be reduced 		    
	*/
	void decreaseStrength(double ratio) 
	{
		for (Protector protector:army)
		{
			protector.decreaseStrength(ratio);
		}
	}
	
	/** die() is the method which is called when the lord battles against other Noble and if he loses the battle
	*/
	void die()
	{
		for (Protector protector: army) 
		{
			protector.decreaseStrength(1.0);
		}
	}
	
	@Override //here we are overriding the toString method to display the lords information
	public String toString()
	{	StringBuilder result = new StringBuilder();
		result.append(this.getNobleName() + " has an army of " + army.size() + "\n " +  "\t");
		for(Protector i : army)
		{
			result.append(i.getProtectorName() + " : " + i.getProtectorStrength() + "\n" +  "\t");
		}
		return result.toString();
	
	}
}
	
	
	
/** Protector is the abstract class which is inherited by both Warrior and Wizard classes*/		
abstract class Protector
{	
	/** strength is the member variable of Protector which is of type String which holds the name of the protector*/
	private String name;
	/** strength is the member variable of Protector which is of type double which holds the strength of the protector*/
	private double strength;
	/** master is the member variable of Protector which is of type Lord which holds the Lord which hires the Protector*/
	private Lord master;
	
	/** Protector(String name,int strength) is a constructor which takes two arguments and initialises the name and strength	
	 @param		name				name of the protector given as a parameter
	 @param		strength			strength of the protector given as a parameter
	*/
	Protector(String name,double strength)
	{
		this.name = name;
		this.strength = strength;
	}
	
	/** fight() is the abstract method which is implemented in both Warrior and Wizard classes
	 */
	abstract void fight();
	
	/** getProtectorName() is a getter method for returning the protectors's name of type String	
	 @return	name		it is the name of the protector		    
	 */
	String getProtectorName(){return this.name;}
	
	/** getProtectorStrength() is a getter method for returning the protectors's strength of type double	
	 @return	strength		it is the strength of the protector		    
	 */
	double getProtectorStrength(){return this.strength;}
	
	/** decreaseStrength(double ratio) is the method which is called when a Lord battle against another noble and the strengths of 
		protectors should be decreased in a certain ratio
	 @param		ratio		it is the ratio based on which the protectors strength is to be decreased 
	*/
	void decreaseStrength(double ratio)
	{
		strength *= 1-ratio;
	}
	
	/** getMaster() is a getter method for returning the Lord name of type Lord	
	 @return	master		it is the Lord who hired the protector		    
	 */
	Lord getMaster(){return this.master;}
	
	/** setMaster() is a setter method for setting the lord who hires the protector	
	 @param		master		it is the lord who hires the protector		    
	 */
	void setMaster(Lord master){this.master = master ;}
	
	/** runaway() is the method which is called when a warrior wants to quit his job under a Lord 
	*/
	void runsaway()
	{	
		if (this.strength > 0)
		{
			master.removeSoldierFromArmy(this);
		}
	}
}

/** Warrior class is inherited from Protector class*/
class Warrior extends Protector
{	
	/** Warrior(String name,int strength) is a constructor which takes two arguments and initialises the name and strength	
	 @param		name				name of the warrior given as a parameter
	 @param		strength			strength of the warrior given as a parameter
	*/
	Warrior(String name,double strength)
	{
		super(name,strength);//call to super class constructor
	}
	
	/**	fight() is the implementation of the abstract method in the Protector 
	 */
	void fight()
	{
			System.out.println(this.getProtectorName() +" says: Take that in the name of my lord, " + this.getMaster().getNobleName());	
	}
}

/** Wizard class is inherited from Protector class*/
class Wizard extends Protector
{	
	/** Wizard(String name,int strength) is a constructor which takes two arguments and initialises the name and strength	
	 @param		name				name of the wizard given as a parameter
	 @param		strength			strength of the wizard given as a parameter
	*/
	Wizard(String name,double strength)
	{
		super(name,strength);//call to super class constructor
	}
	
	/**	fight() is the implementation of the abstract method in the Protector 
	 */
	void fight()
	{
		System.out.println("POOF");
	}

}

/** Archer class is inherited from Warrior class*/
class Archer extends Warrior
{	
	/** Archer(String name,int strength) is a constructor which takes two arguments and initialises the name and strength	
	 @param		name				name of the archer given as a parameter
	 @param		strength			strength of the archer given as a parameter
	*/
	Archer(String name,double strength)
	{
		super( name,strength);//call to super class constructor
	}
	
	/**	fight() is the method which overrides the method in the warrior
	 */
	void fight()
	{
		System.out.print("TWANG!");
		super.fight();//call to super class fight method
	}
}

/** Archer class is inherited from Warrior class*/
class Swordsman extends Warrior
{
	/** Swordsman(String name,int strength) is a constructor which takes two arguments and initialises the name and strength	
	 @param		name				name of the swordsman given as a parameter
	 @param		strength			strength of the swordsman given as a parameter
	*/
	Swordsman(String name,double strength)
	{
		super(name,strength);//call to super class constructor
	}
	
	/**	fight() is the method which overrides the method in the warrior
	 */
	void fight()
	{
		System.out.print("CLANG!");
		super.fight();//call to super class fight method
	}
}

class WarrirorsNoblesInheritance {
    public static void main(String[] args) {

        Lord sam = new Lord("Sam");
        Lord joe = new Lord("Joe");
        Lord janet = new Lord("Janet");

        PersonWithStrength randy = new PersonWithStrength("Randolf the Elder", 250);
        PersonWithStrength barclay = new PersonWithStrength("Barclay the Bold", 300);

        Swordsman hardy = new Swordsman("TuckTuckTheHardy", 60);
        Swordsman stout = new Swordsman("TuckTuckTheStout", 40);
        Archer samantha = new Archer("Samantha", 50);
        Archer pethora = new Archer("Pethora", 50);	
        Wizard thora = new Wizard("Thorapleth", 70);
 
        sam.hire(samantha);
        janet.hire(hardy);	
        janet.hire(stout);
	
        janet.hire(thora);
        joe.battle(randy);        
        joe.battle(sam);	
		//janet.battle(sam);
        janet.battle(barclay);
        janet.hire(samantha);	
        janet.hire(pethora);
		
		
        janet.battle(barclay);	
        sam.battle(barclay);	
        joe.battle(barclay);

        thora.runsaway();
		//joe.battle(sam);
        System.out.println(sam);
        System.out.println(joe);
        System.out.println(janet);
        System.out.println(randy);
        System.out.println(barclay);
    }    
}