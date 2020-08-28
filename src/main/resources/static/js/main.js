$(document).ready(function () {
    $('#currency2').on('change', async function () {
        let body = {
            firstCharCode: $("#currency1").val(),
            secondCharCode: $("#currency2").val(),
        }
        let response = await fetch('/currency/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        calculate(result)
    })
    $('#currency1').on('change', async function () {
        let body = {
            firstCharCode: $("#currency1").val(),
            secondCharCode: $("#currency2").val(),
        }
        let response = await fetch('/currency/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        calculate(result)
    })
    $('#firstCurrencyOutput').on('input', async function () {
        let body = {
            firstCharCode: $("#currency1").val(),
            secondCharCode: $("#currency2").val(),
        }
        let response = await fetch('/currency/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        calculate(result)
    })
})

function calculate(result) {
    let amount = $("#firstCurrencyOutput").val()
    if (!amount) {
        amount = 1;
    }
    document.currencyForm.result.value = (Math.trunc((
        result['firstValue'].replace(/,/, '.') * result['secondNominal'] * amount) /
        (result['secondValue'].replace(/,/, '.') * result['firstNominal'])*1000)/1000).toLocaleString('ru')
}