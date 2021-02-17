package com.tudor.Model.Expressions;

import com.tudor.Exceptions.OperationNonExistent;
import com.tudor.Exceptions.TypeExceptions.TypeMismatch;
import com.tudor.Model.ADTs.IDict;
import com.tudor.Model.ADTs.IHeap;
import com.tudor.Model.Types.BoolType;
import com.tudor.Model.Types.IntType;
import com.tudor.Model.Types.Type;
import com.tudor.Model.Values.BoolValue;
import com.tudor.Model.Values.IntValue;
import com.tudor.Model.Values.StringValue;
import com.tudor.Model.Values.Value;

public class RelationalExpression implements Expression{
    private final Expression firstExpression;
    private final Expression secondExpression;
    final StringValue operation;

    public RelationalExpression(Expression firstExpression, Expression secondExpression, StringValue operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public String toString(){
        return switch (operation.getValue()) {
            case "<" -> firstExpression.toString() + "<" + secondExpression.toString();
            case "<=" -> firstExpression.toString() + "<=" + secondExpression.toString();
            case "==" -> firstExpression.toString() + "==" + secondExpression.toString();
            case "!=" -> firstExpression.toString() + "!=" + secondExpression.toString();
            case ">" -> firstExpression.toString() + ">" + secondExpression.toString();
            case ">=" -> firstExpression.toString() + ">=" + secondExpression.toString();
            default -> "";
        };
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        Value value1, value2;
        value1 = firstExpression.evaluate(symTable, heap);
        if(value1.getType().equals(new IntType() ) ){
            value2 = secondExpression.evaluate(symTable, heap);
            if(value2.getType().equals(new IntType() ) ){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation.getValue()){
                    case "<":
                        if(number1 < number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "<=":
                        if(number1 <= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "==":
                        if(number1 == number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "!=":
                        if(number1 != number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">":
                        if(number1 > number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">=":
                        if(number1 >= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
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
                return new BoolType();
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new TypeMismatch();
        }
    }

}
