import request from '../request.js'

export default {
    login: (form) => {
        return request({
            url: '/login',
            method: 'post',
            data: form
        })

    },
    enroll: (form) => {
        return request({
            url: '/enroll',
            method: 'post',
            data: form
        })
    },
    sendCode: (form) => {
        console.log(form)
        return request({
            url: '/emailVerify',
            method: 'post',
            params: form
        })
    }
}