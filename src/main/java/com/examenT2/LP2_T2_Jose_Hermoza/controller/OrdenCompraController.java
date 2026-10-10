package com.examenT2.LP2_T2_Jose_Hermoza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.examenT2.LP2_T2_Jose_Hermoza.model.OrdenCompraHermozaJose;
import com.examenT2.LP2_T2_Jose_Hermoza.service.OrdenCompraService;
import com.examenT2.LP2_T2_Jose_Hermoza.service.ProveedorService;
import com.examenT2.LP2_T2_Jose_Hermoza.util.Alert;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("ordenCompra")
@RequiredArgsConstructor
public class OrdenCompraController {

	private final OrdenCompraService ordenCompraService;
	private final ProveedorService proveedorService;

	@InitBinder("ordenCompra")
	public void configurarFormulario(WebDataBinder binder) {
		binder.setAllowedFields("nroOrden", "proveedor", "monto", "direccionEntrega", "fechaEntrega", "estado");
	}

	@GetMapping("listado")
	public String listado(Model model) {
		model.addAttribute("lstOrdenesCompra", ordenCompraService.getAll());
		return "listadoHermozaJose";
	}

	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("lstProveedores", proveedorService.getAll());
		// Inicializa el objeto del formulario, como en el proyecto original.
		model.addAttribute("ordenCompra", new OrdenCompraHermozaJose());
		return "nuevoHermozaJose";
	}

	@PostMapping("registrar")
	public String registrar(@ModelAttribute("ordenCompra") OrdenCompraHermozaJose ordenCompra, BindingResult errores,
			Model model, RedirectAttributes flash) {
		if (errores.hasErrors()) {
			model.addAttribute("lstProveedores", proveedorService.getAll());
			model.addAttribute("mensajeError", "Revise los datos ingresados en el formulario.");
			return "nuevoHermozaJose";
		}
		var response = ordenCompraService.create(ordenCompra);
		if (!response.success()) {
			model.addAttribute("lstProveedores", proveedorService.getAll());
			model.addAttribute("mensajeError", response.mensaje());
			return "nuevoHermozaJose";
		}

		var alert = Alert.sweetToast(response.mensaje(), "success", 5000);
		flash.addFlashAttribute("alert", alert);
		flash.addFlashAttribute("mensajeExito", response.mensaje());
		return "redirect:/ordenCompra/listado";
	}

	@GetMapping("edicion/{numero}")
	public String edicion(@PathVariable Integer numero, Model model, RedirectAttributes flash) {
		var ordenCompra = ordenCompraService.getOne(numero);
		if (ordenCompra == null) {
			flash.addFlashAttribute("alert", Alert.sweetToast("La orden de compra no existe.", "error", 5000));
			return "redirect:/ordenCompra/listado";
		}
		model.addAttribute("lstProveedores", proveedorService.getAll());
		model.addAttribute("ordenCompra", ordenCompra);
		return "edicionHermozaJose";
	}

	@PostMapping("guardar")
	public String guardar(@ModelAttribute("ordenCompra") OrdenCompraHermozaJose ordenCompra, BindingResult errores,
			Model model, RedirectAttributes flash) {
		if (errores.hasErrors()) {
			model.addAttribute("lstProveedores", proveedorService.getAll());
			model.addAttribute("mensajeError", "Revise los datos ingresados en el formulario.");
			return "edicionHermozaJose";
		}
		var response = ordenCompraService.update(ordenCompra);
		if (!response.success()) {
			model.addAttribute("lstProveedores", proveedorService.getAll());
			model.addAttribute("mensajeError", response.mensaje());
			return "edicionHermozaJose";
		}

		var alert = Alert.sweetToast(response.mensaje(), "success", 5000);
		flash.addFlashAttribute("alert", alert);
		flash.addFlashAttribute("mensajeExito", response.mensaje());
		return "redirect:/ordenCompra/listado";
	}
}
