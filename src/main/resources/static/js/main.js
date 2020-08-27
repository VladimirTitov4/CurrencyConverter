$(document).ready(function () {
    $('#currency2').on('change', async function () {
        let body = {
            firstCharCode: $("#currency1" ).val(),
            amount: $("#firstCurrencyOutput").val(),
            secondCharCode: $(this).val()
        }
        let response = await fetch('/currency/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        document.currencyForm.secondValue.value = result['result']
    })
    $('#currency1').on('change', async function () {
        let body = {
            firstCharCode: $("#currency2" ).val(),
            amount: $("#firstCurrencyOutput").val(),
            secondCharCode: $(this).val()
        }
        let response = await fetch('/currency/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        document.currencyForm.secondValue.value = result['result']
    })
    $('#firstCurrencyOutput').on('input', async function () {
        let body = {
            firstCharCode: $("#currency1" ).val(),
            amount: $("#firstCurrencyOutput").val(),
            secondCharCode: $("#currency2" ).val()
        }
        let response = await fetch('/currency/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(body)
        })
        let result = await response.json()
        document.currencyForm.secondValue.value = result['result']
    })
})