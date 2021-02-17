package com.tudor.Model.Statements;

import com.tudor.Exceptions.TypeExceptions.ConditionNotBoolType;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.ADTs.IStack;
import com.tudor.Model.ADTs.MyDict;
import com.tudor.Model.Expressions.Expression;
import com.tudor.Model.ProgramState;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.Value;

public class WhileStatement implements Statement{
    Expression expression;
    Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    /**
     * If expression is evaluated as true then a another WhileStatement is pushed onto the state's Stack
     * followed by statement.
     * @param state (type ProgramState)
     * @return null
     * @throws ConditionNotBoolType if expression.evaluate is not BoolValue(which has BoolType)
     */
    @Override
    public ProgramState execute(ProgramState state) {
        IStack<Statement> stack = state.getStack();
        IDict<String, Value> symbolTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value condition = expression.evaluate(symbolTable,heap);
        if (!condition.getType().equals(new BoolType())){
            throw new ConditionNotBoolType();
        }

        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getValue())
        {
            //true
            stack.push(new WhileStatement(expression, statement));
            stack.push(statement);
        }
        return null;
    }

    @Override
    public String toStringAsCode() {
        return "while(" + expression.toString() +"){\n\t" + statement.toString() + ";\n}";
    }

    @Override
    public IDict<String, Type> typecheck(IDict<String, Type> typeEnvironment) {
        Type typeExpression = expression.typecheck(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            statement.typecheck(new MyDict<>((MyDict<String, Type>) typeEnvironment));
            return typeEnvironment;
        }else{
            throw new ConditionNotBoolType();
        }
    }

    @Override
    public String toString() {
        return "While("
                + expression.toString() + ") "
                + " (" + statement.toString() + ")";
    }
}
