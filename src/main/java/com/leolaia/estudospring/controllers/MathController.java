package com.leolaia.estudospring.controllers;

import com.leolaia.estudospring.exceptions.UnsuportedMathOperationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class MathController {

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }
    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }
    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }
    @RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }
    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws Exception{
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
    }
    @RequestMapping(value = "/squareRoot/{number}", method = RequestMethod.GET)
    public Double squareRoot(
            @PathVariable(value = "number") String number
    ) throws Exception{
        if (!isNumeric(number))
            throw new UnsuportedMathOperationException("Please set a numeric value");
        return Math.sqrt(convertToDouble(number));
    }
    private Double convertToDouble(String strNumber) {
        if (strNumber == null) return 0D;
        String number = strNumber.replaceAll(",", ".");
        if(isNumeric(number)) return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[+-]?[0-9]*\\.?[0-9]+");
    }
}
