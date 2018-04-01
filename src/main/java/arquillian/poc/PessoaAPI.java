package arquillian.poc;

import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/pessoa")
public class PessoaAPI {

	@EJB
	private PessoaDao pessoaDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> getAll() {
		return this.pessoaDao.buscarTodasPessoas();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") int id) {
		try {

			Pessoa pessoa = this.getByPessoaId(id);

			return Response.status(Status.OK).entity(pessoa).build();

		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.NOT_FOUND).entity(json).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(PessoaForm form) {
		System.out.println("PessoaAPI.save() -> " + form);
		try {

			this.formValidate(form);

			Pessoa existente = this.pessoaDao.recuperarPorNome(form.getNome());
			
			if (existente != null) {
				throw new IllegalArgumentException("{".concat(form.getNome()).concat("} ja cadastrado"));
			}
			
			this.pessoaDao.salvar(new Pessoa(form.getNome(), form.getIdade()));

			return Response.status(Status.CREATED).build();

		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, PessoaForm form) {
		System.out.println("PessoaAPI.update() -> " + form);
		try {

			Pessoa pessoa = this.getByPessoaId(id);

			this.formValidate(form);

			pessoa.setIdade(form.getIdade());
			pessoa.setNome(form.getNome());

			this.pessoaDao.atualizar(pessoa);

			return Response.status(Status.OK).build();

		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) {
		try {

			Pessoa pessoa = this.getByPessoaId(id);

			this.pessoaDao.remover(pessoa);

			return Response.status(Status.OK).build();

		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.NOT_FOUND).entity(json).build();
		}
	}

	private Pessoa getByPessoaId(int id) {
		Pessoa pessoa = this.pessoaDao.buscar(id);

		if (pessoa == null) {
			throw new IllegalArgumentException("ID {" + id + "} invalido");
		}

		return pessoa;
	}

	private void formValidate(PessoaForm form) {
		if (form == null || (form.getNome() == null || form.getNome().isEmpty())
				|| (form.getIdade() == null || form.getIdade() <= 0)) {
			throw new IllegalArgumentException("Parametros NOME e IDADE sao obrigatorios");
		}
	}

}
