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
    selectHotelByCityTime: (city,timeStart,timeEnd) => {
        return request({
            url: '/selectHotelByCityTime',
            method: 'post',
            params: {
                city,
                timeStart,
                timeEnd
            }
        })
    }

}