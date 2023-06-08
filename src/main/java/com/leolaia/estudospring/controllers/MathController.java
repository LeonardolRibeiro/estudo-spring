package com.leolaia.estudospring.controllers;

import com.leolaia.estudospring.exceptions.UnsuportedMathOperationException;
import com.leolaia.estudospring.math.SimpleMath;
import org.springframework.web.bind.annotation.*;

import static com.leolaia.estudospring.converters.NumberConverter.convertToDouble;
import static com.leolaia.estudospring.converters.NumberConverter.isNumeric;

@RestController
public class MathController {

    private SimpleMath math = new SimpleMath();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
    }
    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo));
    }
    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo));
    }
    @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.division(convertToDouble(numberOne), convertToDouble(numberTwo));
    }
    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.mean(convertToDouble(numberOne),  convertToDouble(numberTwo));
    }
    @RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)
    public Double squareRoot(
            @PathVariable(value = "number") String number
    ) throws Exception{
        if (!isNumeric(number))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return math.squareRoot(convertToDouble(number));
    }
}
