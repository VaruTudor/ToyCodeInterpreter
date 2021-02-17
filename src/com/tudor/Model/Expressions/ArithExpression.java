package com.tudor.Model.Expressions;

import com.tudor.Exceptions.DivisionByZero;
import com.tudor.Exceptions.OperationNonExistent;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.Value;

public class ArithExpression implements Expression{
    private final Expression firstExpression;
    private final Expression secondExpression;
    final int operation;

    public ArithExpression(Expression firstExpression, Expression secondExpression, int operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public String toString(){
        return switch (operation) {
            case 1 -> firstExpression.toString() + "+" + secondExpression.toString();
            case 2 -> firstExpression.toString() + "-" + secondExpression.toString();
            case 3 -> firstExpression.toString() + "*" + secondExpression.toString();
            case 4 -> firstExpression.toString() + "/" + secondExpression.toString();
            default -> "";
        };
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap)  {
        Value value1, value2;
        value1 = firstExpression.evaluate(symTable, heap);
        if(value1.getType().equals(new IntType() ) ){
            value2 = secondExpression.evaluate(symTable, heap);
            if(value2.getType().equals(new IntType() ) ){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation){
                    case 1:
                        return new IntValue(number1 + number2);
                    case 2:
                        return new IntValue(number1 - number2);
                    case 3:
                        return new IntValue(number1 * number2);
                    case 4:
                        if (number2 == 0){
                            throw new DivisionByZero();
                        }
                        else return new IntValue(number1/number2);
                    default:
                        throw new OperationNonExistent();
                }
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

        if(typeFistExpression.equals(new IntType())){
            if(typeSecondExpression.equals(new IntType())){
                return new IntType();
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new TypeMismatch();
        }
    }


}
