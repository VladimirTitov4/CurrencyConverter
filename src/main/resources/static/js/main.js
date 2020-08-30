function test() {
    let amount = $("#firstCurrencyOutput").val()
    if (!amount) {
        amount = 1;
    }
    document.currencyForm.result.value = (Math.trunc(((($("#currency1").val() * amount) / $("#currency2").val()))*1000)/1000).toLocaleString('ru')

}