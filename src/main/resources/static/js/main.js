function calculate() {

    let currency1 = $("#currency1").val().substring($("#currency1").val().indexOf("|") + 1)
    let currency2 = $("#currency2").val().substring($("#currency2").val().indexOf("|") + 1)
    let amount = $("#firstCurrencyOutput").val()
    if (!amount) {
        amount = 1;
    }
    document.currencyForm.result.value = (Math.trunc((((currency1 * amount) / currency2))*100)/100).toLocaleString('ru')
}