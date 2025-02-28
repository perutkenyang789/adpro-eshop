package id.ac.ui.cs.advprog.eshop.contoller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/car")
public class CarController implements CrudController<Car, String> {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private static final String CAR_LIST_PAGE = "redirect:/car/listCar";

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Override
    @GetMapping("/createCar")
    public String getCreatePage(Model model) {
        model.addAttribute("car", new Car());
        return "CreateCar";
    }

    @Override
    @PostMapping("/createCar")
    public String postCreate(@ModelAttribute Car car, Model model) {
        carService.create(car);
        return CAR_LIST_PAGE;
    }

    @Override
    @GetMapping("/listCar")
    public String getListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "CarList";
    }

    @Override
    @GetMapping("/editCar/{carId}")
    public String getEditPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "EditCar";
    }

    @Override
    @PostMapping("/editCar/{carId}")
    public String postUpdate(@PathVariable String carId, @ModelAttribute Car car, Model model) {
        logger.info("Car ID: {}", car.getCarId());
        carService.update(carId, car);
        return CAR_LIST_PAGE;
    }

    @Override
    @GetMapping("/deleteCar/{carId}")
    public String postDelete(@PathVariable String carId) {
        carService.deleteCarById(carId);
        return CAR_LIST_PAGE;
    }
}