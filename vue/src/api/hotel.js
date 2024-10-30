import request from '../request.js'

export default {
    addHotel: (form) => {
        return request({
            url: '/addHotel',
            method: 'post',
            data: form
        })
    },
    deletePicture: (fileName) => {
        return request({
            url: '/deletePicture',
            method: 'post',
            params: {
                fileName
            }
        })
    },
    selectHotel: () => {
        return request({
            url: '/selectSpecialsHotels',
            method: 'get'
        })
    },
    selectHotelBySearchBox: (form) => {
        return request({
            url: '/selectHotelBySearchBox',
            method: 'post',
            data: form
        })
    },
    booking: (form) => {
        return request({
            url: '/booking',
            method: 'post',
            data: form
        })
    }
}