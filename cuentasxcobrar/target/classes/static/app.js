$(document).ready(function(){
    connect();
    console.log("hola mundo")
    const clientes = document.querySelector(".cliente");
    const fechasSelect = document.querySelector(".clientefechas");
    clientes.addEventListener("change", function (){
        let fechas = data[clientes.value]['fecha']
        let fechasUnicas = eliminaDuplicados(fechas)
        limpiar()
        const optionDefault = document.createElement('option');
        optionDefault.value = "-1"
        optionDefault.text = "Elija una fecha"
        optionDefault.selected
        fechasSelect.appendChild(optionDefault)
        fechasUnicas.forEach(fe => {
            const option = document.createElement('option');
            option.value = fechasUnicas.indexOf(fe).toString()
            option.text = fe.toString()
            fechasSelect.appendChild(option)
        })

    })
    const search = document.querySelector(".search");
    search.addEventListener("click", function (){
        let cli = clientes.value;
        let fec = fechasSelect.options[fechasSelect.selectedIndex].text;
        let dataCliente = data[cli]['cuentasxcobrar']
        let dataClienteFecha = [];
        dataCliente.forEach(d =>{
            if(d["fecha"] === fec){
                dataClienteFecha.push(d)
            }
        })
        const bodyTable = document.querySelector(".body");
        bodyTable.innerHTML = "";
        dataClienteFecha.forEach(dd =>{
            const tr = document.createElement("TR");
            tr.innerHTML = `<tr>
                    <th scope="row">${dataClienteFecha.indexOf(dd)+1}</th>
                    <td>${dd["codigo"]}</td>
                    <td>${dd["importe"]}</td>
                </tr>`;
            bodyTable.appendChild(tr);
        })

    });
    const limpiar = () => {
        for (let i = fechasSelect.options.length; i >= 0; i--) {
            fechasSelect.options.remove(i);
        }
    };
    const eliminaDuplicados = (arr) => {
        return arr.filter((valor, indice) => {
            return arr.indexOf(valor) === indice;
        });
    }
});



function connect(){
    var socket = new SockJS('/websocket-connect2');
    stompClient = Stomp.over(socket);
    stompClient.connect({},function (frame){
        stompClient.subscribe('/topic2/in-memory', function (data){
            alert(data.body)
            /*let estado = document.querySelector(".estado");
            const p = document.createElement("DIV");
            var respuesta = JSON.parse(data.body)
            if(respuesta['codigo']==='2'){
                p.classList.add(
                    "alert",
                    "alert-success",
                    "alert-dismissible",
                    "w-75",
                    "fade",
                    "show",
                );
                p.innerHTML =
                    `<strong>"${respuesta['mensaje']}"</strong> Espere la fecha de entrega
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`
                ;
            } else{
                p.classList.add(
                    "alert",
                    "alert-danger",
                    "alert-dismissible",
                    "w-75",
                    "fade",
                    "show"
                );
                p.innerHTML =
                    `<strong>"${respuesta['mensaje']}"</strong> Corrija para que la orden se procese
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`
                ;
            }
            //p.textContent = data.body;
            //p.classList.add("recibido")
            estado.appendChild(p);*/
        })
    })
}
