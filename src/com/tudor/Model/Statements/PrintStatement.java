package com.tudor.Model.Statements;

import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.ADTs.IList;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.Value;

public class PrintStatement implements Statement{
    private final Expression expression;

    public PrintStatement(Expression v) {
        this.expression = v;
    }

    @Override
    public String toString(){
        return  "print(" + expression.toString() + ")  ";
    }

    public String toStringAsCode() {
        return  "print(" + expression.toString() + ");";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }

    /**
     * the expression is added to the state's outputList
     * @param state (type ProgramState)
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState state)  {
        IDict<String,Value> symbolTable = state.getSymTable();
        IList<Value> out = state.getList();
        IHeap<Integer, Value> heap = state.getHeap();

        out.add(expression.evaluate(symbolTable,heap));
        return null;
    }
}
