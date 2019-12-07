package pe.com.sanluis.clinicasanluis.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import pe.com.sanluis.clinicasanluis.entity.Paciente;
import pe.com.sanluis.clinicasanluis.repository.PacienteRepository;

@Component(value = "pacienteView")
@ViewScoped
public class PacienteView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteRepository pacienteRepository;

	private List<Paciente> pacientes;
	private Paciente paciente;

//	@PostConstruct
//	public void init(int id) {
//		paciente = pacienteRepository.findById(id).get();
//		paciente = new Paciente();
//	}

	@PostConstruct
	public void init() {
		pacientes = pacienteRepository.findAll();
		paciente = new Paciente();
	}

	@NotEmpty(message = "Usuario en blanco")
	private String email;
	@NotEmpty(message = "Contraseña en blanco")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public void buscar(int id) {
		paciente = pacienteRepository.findById(id).get();
	}

	public String login() {
		Paciente paciente = pacienteRepository.login(email, password);
		if (paciente != null) {
			return "dashboard";
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrecta",
					null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "login";
		}
	}

	public void registrar() {
		boolean exits = pacienteRepository.existsById(paciente.getIdPaciente());
		paciente.setEstado("H");
		pacienteRepository.save(paciente);
		if (exits) {
			addMessage("Sistema", "Se ha actualizado satisfactoriamente.");
		} else {
			addMessage("Sistema", "Se ha registrado satisfactoriamente.");
		}
//		init(paciente.getIdPaciente());
		init();
	}
	
	public String registrarPaciente() {
		boolean exits = pacienteRepository.existsById(paciente.getIdPaciente());
		paciente.setEstado("H");
		pacienteRepository.save(paciente);
		if (exits) {
			addMessage("Sistema", "Se ha actualizado satisfactoriamente.");
		} else {
			addMessage("Sistema", "Se ha registrado satisfactoriamente.");
		}
		return "login?facesRedirect=true";
	}

	public void eliminar(int id) {
		pacienteRepository.deleteById(id);
		addMessage("Sistema", "Se ha eliminado satisfactoriamente.");
//		init(paciente.getIdPaciente());
		init();
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
