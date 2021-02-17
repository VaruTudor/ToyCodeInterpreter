package com.tudor.Model.Statements;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.MyDict;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;

public interface Statement {
    /**
     * the ProgramState received as a parameter is modified in a specific way and then returned
     * @return a modified ProgramState
     */
    ProgramState execute(ProgramState state) ;
    String toStringAsCode();
    IDict<String, Type> typecheck(IDict<String,Type> typeEnvironment);
}
