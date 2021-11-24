import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const deleteScheduleById = async (id) => {

    const config = {
        method: 'DELETE',
        url: `${backEndBaseUri}/admin/api/schedule/${id}`,
        withCredentials: true,
    }

    return await axios(config);
};

export { deleteScheduleById };