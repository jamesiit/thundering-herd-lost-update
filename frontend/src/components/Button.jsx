import {toast} from "sonner";
import {useMutation, useQuery} from "@tanstack/react-query";
import {useEffect, useState} from "react";

export default function Button() {
    const [count] = useState(() => Math.floor(Math.random() * 10000) + 1);
    const [isPolling, setIsPolling] = useState(false);

    let genUserId = "user_" + count;
    const getTime = new Date().toLocaleTimeString();
    const quantity = 1;

    const mutation = useMutation({

        mutationFn: data => {
            return fetch("http://localhost:8080/", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                      userId: genUserId,
                      clientQuantity: quantity,
                      clientTime: getTime,
                    }
                )}).then( response => {
                    if (!response.ok) {
                        const error = new Error("An error occurred while fetching");

                        error.response = response

                        throw error;
                    }

                    return response.json()
            })
        },
        onMutate: customFn => {
            return (
                toast.loading("Loading", { id: 'fetched-data'})
            )
        },
        onSuccess: data => {

            toast.success("Order received! You are in the queue", { id: 'fetched-data'})
            setIsPolling(true);

        },
        onError: error => {

            if (error.response?.status === 409) {
                toast.error("Tickets are sold out!", {id: 'fetched-data'});
                return
            }

            return (
            toast.error("Something went wrong!", { id: 'fetched-data'})
            )
        },
    });

    const {data: statusData} = useQuery({
        queryKey: ['orderStatus', genUserId],
        queryFn: () => fetch(`http://localhost:8080/status/${genUserId}`).then(response => response.json()),
        enabled: isPolling, //enables depending on the state of isPolling
        refetchInterval: (data) => {
            if (data?.status === "COMPLETED" || data?.status === "FAILED") {
                return false;
            }
            return 500;
        }
    })

    //useEffect here is a watchdog. Based on the change of state of statusData, it runs the block...
    useEffect(() => {
        if (statusData?.status === "COMPLETED") {
            toast.success("Success! Ticket Secured!", {id: 'fetched-data'})
            setIsPolling(false);
        } else if (statusData?.status === "FAILED") {
            toast.error("Failed! Tickets sold out while you were in the queue", {id: 'fetched-data'})
            setIsPolling(false);
        }
    }, [statusData]);


    return (
        <div className="flex h-screen items-center justify-center">
        <button onClick={() => mutation.mutate()} disabled={statusData?.status === "COMPLETED" || statusData?.status === "FAILED"} className="bg-linear-to-r
        from-indigo-600 to-purple-600 text-white
        px-10 py-5 rounded-xl shadow-xl
        hover:shadow-2xl transition-all duration-300 hover:scale-105 text-3xl font-semibold tracking-wide "> {mutation.isPending ? "Sending..." : "Buy now!"} </button>
        </div>
    )
}