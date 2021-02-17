package com.tudor.Model.Statements;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;

@SuppressWarnings("unused")
public class NopStatement implements Statement{

    /**
     * @param state (type ProgramState)
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toStringAsCode() {
        return "Nop Statement  ";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        return typeEnvironment;
    }

}
