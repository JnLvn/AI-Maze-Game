package ie.gmit.sw.maze;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Fight {

	// Load from 'FCL' file
    String fileName = "fcl/fightScene.fcl";
    FIS fis = FIS.load(fileName,true);
	
	public Fight() {
		super();
	}
	
	public int fuzzyFight(Enemy e, int weapon) {
		
		FIS fis = FIS.load(fileName,true);
        System.out.println("FCL File loaded"); // check for fcl file

        FunctionBlock functionBlock = fis.getFunctionBlock("fightScene");
        // set inputs
        fis.setVariable("enemy", e.getStrength());
        System.out.println("Enemy Set For Fight");
        fis.setVariable("weapon", weapon);
        
        Variable damage = functionBlock.getVariable("damage");
        fis.evaluate();
        
        int d = (int)damage.getValue();
        System.out.println("Damage = " + d);
        return d;
	}
	
}
