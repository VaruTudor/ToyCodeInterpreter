package com.tudor.Model.Expressions;

import com.tudor.Exceptions.OperationNonExistent;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.Value;

public class LogicExpression implements Expression{
    private final Expression firstExpression;
    private final Expression secondExpression;
    private final int operation;

    public LogicExpression(Expression firstExpression, Expression secondExpression, int op) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = op;
    }

    /*
    * 1 - && (AND)
    * 2 - || (OR)
    * */
    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        Value value1, value2;
        value1 = firstExpression.evaluate(symTable, heap);
        if(value1.getType().equals(new BoolType() ) ){
            value2 = secondExpression.evaluate(symTable, heap);
            if(value2.getType().equals(new BoolType() ) ){

                BoolValue boolValue1 = (BoolValue) value1;
                BoolValue boolValue2 = (BoolValue) value2;

                boolean element1 = boolValue1.getValue();
                boolean element2 = boolValue2.getValue();

                return switch (operation) {
                    case 1 -> new BoolValue(element1 && element2);
                    case 2 -> new BoolValue(element1 || element2);
                    default -> throw new OperationNonExistent();
                };
            }
            else {
                throw new TypeMismatch();
            }
        }
        else{
            throw new TypeMismatch();
        }


    }

    @Override
    public Type typecheck(IDict<String, Type> typeEnvironment) {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = firstExpression.typecheck(typeEnvironment);
        typeSecondExpression = secondExpression.typecheck(typeEnvironment);

        if(typeFistExpression.equals(new BoolType())){
            if(typeSecondExpression.equals(new BoolType())){
                return new BoolType();
            }else{
                throw new TypeMismatch();
            }
        }else {
            throw new TypeMismatch();
        }
    }

    @Override
    public String toString() {
        return switch (operation){
            case 1 -> firstExpression.toString() + "&&" + secondExpression.toString();
            case 2 -> secondExpression.toString() + "||" + secondExpression.toString();
            default -> "";
        };
    }
}
