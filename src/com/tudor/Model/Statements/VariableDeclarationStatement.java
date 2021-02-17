package com.tudor.Model.Statements;

import com.tudor.Exceptions.VariableAlreadyDeclared;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public class VariableDeclarationStatement implements Statement{
    String name;
    Type type;

    public VariableDeclarationStatement(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){

        return type + " " + name +"  ";
    }

    @Override
    public String toStringAsCode() {
        return type + " " + name +"; ";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        typeEnvironment.add(name, type);
        return typeEnvironment;
    }

    /**
     * Creates key name in state's SymbolTable
     * and assigns is defaultValue of type.
     * @param state (type ProgramState)
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IDict<String, Value> symTable = state.getSymTable();

        if (symTable.isDefined(name) ){
            throw new VariableAlreadyDeclared();
        }
        else{
            symTable.add(name,type.defaultValue());
        }

        return null;
    }
}
