import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getMyRmById = async (id) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/api/my-rm/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};

const updateMyRm = async (MyRmDto) => {

    const data = {
        id: MyRmDto.id,
        name: MyRmDto.name,
        repetition: MyRmDto.repetition,
        lb: MyRmDto.lb
    }

    const config = {
        method: 'PUT',
        url: `${backEndBaseUri}/api/my-rm`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

const deleteMyRmById = async (id) => {

    const config = {
        method: 'DELETE',
        url: `${backEndBaseUri}/api/my-rm/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};


export { getMyRmById, updateMyRm, deleteMyRmById };